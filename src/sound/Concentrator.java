package sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import sound.generation.Generator;
import sound.generation.SampleGenerator;

public class Concentrator {
	// Temps de référence en nombre de sample depuis le debut du stream
	int time = 0;

	final SourceDataLine soundLine;
	final int bufferSize;
	final byte[] buffer;

	SampleGenerator generator;
	
	public Concentrator(Generator generator) throws LineUnavailableException{
		this(new SampleGenerator(generator, generator));
	}

	public Concentrator(SampleGenerator generator) throws LineUnavailableException {
		this.generator = generator;
		
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
	}

	public double getMsTime() {
		return time / (Sample.sampleRate * Sample.channels);
	}

	public void listen() {
		Thread thread = new Thread() {
			public void run() {
				System.out.println("Concentrator on-air");

				while (true) {
					int sizeToWrite = Math.max(soundLine.available(), bufferSize);
					for (int i = 0; i < sizeToWrite; i++) {
						buffer[i] = 0;
					}

					int i = 0;
					while (i + Sample.byteLength - 1 < sizeToWrite) {
						// Creating sample
						Sample s;
						if (generator.hasNext())
							s = generator.next();
						else
							s = new Sample();
						
						// Copying sample's bytes to buffer
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
