package test;

public class SquareWave extends Generateur {
	float freq = 0;

	int current;
	int tresh;

	float currentTime; // second
	short intensity; // absolute value
	float duration; // second

	public SquareWave(float freq, int intensity, float duration) {
		current = 0;
		currentTime = 0;
		this.freq = freq;
		tresh = (int) (Sample.sampleRate / freq);
		this.intensity = (short) (Short.MAX_VALUE * intensity / 100);
		this.duration = duration;
	}

	@Override
	public Sample next() {
		if (current > tresh) {
			intensity = (short) (-1 * intensity);
			current = 0;
		}
		current++;

		currentTime += Sample.samplePeriod;
		return new Sample(intensity, intensity);
	}

	@Override
	public boolean hasNext() {
		if (duration == 0 || currentTime < duration)
			return true;
		else
			return false;
	}
}