package harmony.sound.generation.wave;

import harmony.sound.Sample;
import harmony.sound.generation.WaveGenerator;

public class Sin implements WaveGenerator {
	private WaveGenerator freq;

	private double currAngle;

	public Sin(double freq) {
		this(new Const(freq));
	}

	public Sin(WaveGenerator freq) {
		if (freq == this || freq == null)
			throw new IllegalArgumentException();
		currAngle = 0;
		this.freq = freq;
	}

	@Override
	public void reset() {
		currAngle = 0;
		freq.reset();
	}

	@Override
	public double next() {
		currAngle += Sample.samplePeriod * freq.next();
		currAngle = (float) (currAngle - Math.floor(currAngle));
		return Math.sin(2 * Math.PI * currAngle);
	}

	@Override
	public boolean hasNext() {
		return freq.hasNext();
	}
}
