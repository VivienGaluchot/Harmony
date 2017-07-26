package harmony.sound.generation.mix;

import harmony.sound.Sample;
import harmony.sound.generation.WaveGenerator;

public class DurationAdjuster implements WaveGenerator {

	private WaveGenerator source;

	private double duration; // second
	private double currentTime; // second

	public DurationAdjuster(double duration, WaveGenerator source) {
		this.source = source;
		this.duration = duration;
		currentTime = 0;
	}

	@Override
	public void reset() {
		currentTime = 0;
		source.reset();
	}

	@Override
	public double next() {
		currentTime += Sample.samplePeriod;
		if (source.hasNext())
			return source.next();
		else
			return 0;
	}

	@Override
	public boolean hasNext() {
		if (currentTime < duration)
			return true;
		else
			return false;
	}

}
