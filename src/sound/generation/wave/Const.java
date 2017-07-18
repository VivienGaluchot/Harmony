package sound.generation.wave;

import sound.generation.WaveGenerator;

public class Const implements WaveGenerator {

	double value;

	public Const(double value) {
		this.value = value;
	}

	@Override
	public void reset() {

	}

	@Override
	public double next() {
		return value;
	}

	@Override
	public boolean hasNext() {
		return true;
	}

}
