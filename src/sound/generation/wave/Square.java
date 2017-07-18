package sound.generation.wave;

import sound.Sample;
import sound.generation.WaveGenerator;

public class Square implements WaveGenerator {
	private WaveGenerator freq;

	private int counter;
	private double output;

	public Square(double freq) {
		this(new Const(freq));
	}

	public Square(WaveGenerator freq) {
		if (freq == this || freq == null)
			throw new IllegalArgumentException();
		counter = 0;
		output = 1;
		this.freq = freq;
	}

	@Override
	public void reset() {
		counter = 0;
		output = 1;
		freq.reset();
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