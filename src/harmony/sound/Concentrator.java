//    Harmony : procedural sound waves generator
//    Copyright (C) 2017  Vivien Galuchot
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, version 3 of the License.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

package harmony.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import harmony.sound.generation.SampleGenerator;
import harmony.sound.generation.SampleGeneratorModel;
import harmony.sound.generation.WaveGenerator;
import harmony.sound.generation.mix.Adder;
import harmony.sound.generation.mix.Delayer;
import harmony.sound.generation.mix.DurationAdjuster;
import harmony.sound.generation.mix.Multiplier;
import harmony.sound.generation.mix.Repeater;
import harmony.sound.generation.wave.Linear;
import harmony.sound.generation.wave.Sin;
import harmony.sound.generation.wave.Square;

public class Concentrator {
	int sampleCount = 0;

	final SourceDataLine soundLine;
	final int bufferSize;
	final byte[] buffer;

	SampleGenerator generator;

	public Concentrator(WaveGenerator generator) throws LineUnavailableException {
		this(new SampleGeneratorModel(generator, generator));
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
		return sampleCount / (Sample.sampleRate * Sample.channels);
	}

	public void listen() {
		Thread thread = new Thread() {
			@Override
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

					sampleCount += sizeToWrite / (Sample.channels * (Sample.sampleSizeInBits / 8));
					// the next call is blocking until the entire buffer is
					// sent to the SourceDataLine
					soundLine.write(buffer, 0, sizeToWrite);
				}
			}
		};
		thread.setPriority(Thread.MAX_PRIORITY);
		thread.start();
	}

	public static void main(String[] args) throws Exception {
		// soundwave test
		Adder mainPlayer = new Adder();
		mainPlayer
				.add(new Repeater(2, new Multiplier(new Multiplier(0.3, new Sin(3)), new Sin(new Linear(30, 40, 2)))));

		Adder subAdder = new Adder();
		subAdder.add(new DurationAdjuster(1,
				new Multiplier(new Multiplier(0.05, new Sin(2)), new Square(new Linear(80, 100, 1)))));
		subAdder.add(new Delayer(2,
				new DurationAdjuster(2, new Multiplier(new Multiplier(0.05, new Sin(2)), new Square(100)))));

		mainPlayer.add(new Repeater(4, subAdder));

		Concentrator c = new Concentrator(mainPlayer);
		c.listen();
	}
}
