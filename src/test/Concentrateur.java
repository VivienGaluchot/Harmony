package test;

import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Concentrateur {
	// Temps de référence en nombre de sample depuis le debut du stream
	int time = 0;

	private final ArrayList<Generateur> generateurs;

	final SourceDataLine soundLine;
	final int bufferSize;
	final byte[] buffer;

	public Concentrateur() throws LineUnavailableException {
		System.out.println("Starting new Concentrateur : ");
		System.out.println(Sample.bytesPerSec + " Bytes/s");
		System.out.println(Sample.samplePerSec + " Sample/s");

		final AudioFormat audioFormat = new AudioFormat(Sample.sampleRate, Sample.sampleSizeInBits, Sample.channels,
				Sample.signed, Sample.bigEndian);
		final DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		soundLine = (SourceDataLine) AudioSystem.getLine(info);

		bufferSize = Sample.bytesPerSec / 100; // in Bytes, 10ms
		soundLine.open(audioFormat, bufferSize);
		soundLine.start();
		buffer = new byte[bufferSize];
		System.out.println(
				"Buffer length : " + bufferSize + " samples, " + 1000 * bufferSize / Sample.bytesPerSec + "ms");

		generateurs = new ArrayList<>();
	}

	public void addGenerateur(Generateur g) {
		synchronized (generateurs) {
			generateurs.add(g);
		}
	}

	public double getMsTime() {
		return time / (Sample.sampleRate * Sample.channels);
	}

	public void listen() {
		Thread thread = new Thread() {
			public void run() {
				System.out.println("Concentrateur on-air");

				ArrayList<Generateur> toRemove = new ArrayList<>();

				while (true) {
					int sizeToWrite = Math.max(soundLine.available(), bufferSize);
					for (int i = 0; i < sizeToWrite; i++) {
						buffer[i] = 0;
					}

					synchronized (generateurs) {
						generateurs.removeAll(toRemove);
						toRemove.clear();
					}

					int i = 0;
					while (i + Sample.byteLength - 1 < sizeToWrite) {
						Sample s = new Sample();
						synchronized (generateurs) {
							for (Generateur g : generateurs) {
								if (g.hasNext())
									s.add(g.next());
								else
									toRemove.add(g);
							}
						}
						for (int j = 0; j < Sample.byteLength; j++) {
							buffer[i + j] += s.toBytes()[j];
						}
						i += Sample.byteLength;
					}

					time += sizeToWrite / (Sample.channels * (Sample.sampleSizeInBits / 8));
					// the next call is blocking until the entire buffer is
					// sent to the SourceDataLine
					soundLine.write(buffer, 0, sizeToWrite);
				}
			}
		};
		thread.start();
	}
}
