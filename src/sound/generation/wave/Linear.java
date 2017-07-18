package sound.generation.wave;

import sound.Sample;
import sound.generation.WaveGenerator;

public class Linear implements WaveGenerator {

	private double initValue;
	private double endValue;
	private double duration; // second
	
	private double currentTime; // second

	public Linear(double initValue, double endValue, double duration) {
		this.initValue = initValue;
		this.endValue = endValue;
		this.duration = duration;
		currentTime = 0;
	}

	@Override
	public void reset() {
		currentTime = 0;
	}

	@Override
	public double next() {
		currentTime += Sample.samplePeriod;
		return (currentTime / duration) * (endValue - initValue) + initValue;
	}

	@Override
	public boolean hasNext() {
		if (currentTime < duration)
			return true;
		else
			return false;
	}

}
