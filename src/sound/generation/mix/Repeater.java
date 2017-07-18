package sound.generation.mix;

import sound.generation.WaveGenerator;

public class Repeater extends DurationAdjuster implements WaveGenerator {

	public Repeater(double duration, WaveGenerator source) {
		super(duration, source);
	}

	@Override
	public void reset() {
		super.reset();
	}

	@Override
	public double next() {
		return super.next();
	}

	@Override
	public boolean hasNext() {
		if (!super.hasNext())
			reset();
		return true;
	}

}
