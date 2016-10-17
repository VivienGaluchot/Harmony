package sound.generation.mix;

import sound.Sample;
import sound.generation.Generator;

public class DurationAdjuster implements Generator {

	private Generator source;

	private double duration; // second
	private float currentTime; // second

	public DurationAdjuster(double duration, Generator source) {
		this.source = source;
		this.duration = duration;
	}

	@Override
	public double next() {
		currentTime += Sample.samplePeriod;
		return source.next();
	}

	@Override
	public boolean hasNext() {
		if (source.hasNext() && currentTime < duration)
			return true;
		else
			return false;
	}

}
