package sound.generation.mix;

import sound.Sample;
import sound.generation.WaveGenerator;

public class Delayer implements WaveGenerator {

	private WaveGenerator source;

	private double duration; // second
	private double currentTime; // second

	public Delayer(double duration, WaveGenerator source) {
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
		if (currentTime < duration) {
			currentTime += Sample.samplePeriod;
			return 0;
		} else {
			return source.next();
		}
	}

	@Override
	public boolean hasNext() {
		return source.hasNext();
	}

}
