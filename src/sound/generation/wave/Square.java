package sound.generation.wave;

import sound.Sample;
import sound.generation.Generator;

public class Square implements Generator {
	private Generator freq;

	private int counter;
	private double output;
	
	public Square(double freq) {
		this(new Const(freq));
	}

	public Square(Generator freq) {
		counter = 0;
		output = 1;
		this.freq = freq;
	}

	@Override
	public double next() {
		if (counter > Sample.sampleRate / freq.next()) {
			output = (short) (-1 * output);
			counter = 0;
		}
		counter++;
		return output;
	}

	@Override
	public boolean hasNext() {
		return freq.hasNext();
	}
}