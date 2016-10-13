package sound.waveMix;

import sound.Generator;
import sound.Sample;

public class DurationAdjuster implements Generator {

	private Generator source;

	private float duration; // second
	private float currentTime; // second

	public DurationAdjuster(Generator source, float duration) {
		this.source = source;
		this.duration = duration;
	}

	@Override
	public Sample next() {
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
