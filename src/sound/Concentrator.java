package sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import sound.waveMix.WaveAdder;

public class Concentrator {
	// Temps de référence en nombre de sample depuis le debut du stream
	int time = 0;

	final SourceDataLine soundLine;
	final int bufferSize;
	final byte[] buffer;

	WaveAdder mainPlayer;

	public Concentrator() throws LineUnavailableException {
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

		mainPlayer = new WaveAdder();
	}

	public void addGenerator(Generator g) {
		mainPlayer.add(g);
	}

	public double getMsTime() {
		return time / (Sample.sampleRate * Sample.channels);
	}

	public void listen() {
		Thread thread = new Thread() {
			public void run() {
				System.out.println("Concentrateur on-air");

				while (true) {
					int sizeToWrite = Math.max(soundLine.available(), bufferSize);
					for (int i = 0; i < sizeToWrite; i++) {
						buffer[i] = 0;
					}

					int i = 0;
					while (i + Sample.byteLength - 1 < sizeToWrite) {
						Sample s;
						if (mainPlayer.hasNext())
							s = mainPlayer.next();
						else
							s = new Sample();
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
