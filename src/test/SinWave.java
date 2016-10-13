package test;

public class SinWave extends Generateur {
	float freq = 0;

	float currentTime; // second
	short intensity; // absolute value
	float duration; // second

	public SinWave(float freq, int intensity, float duration) {
		currentTime = 0;
		this.freq = freq;
		this.intensity = (short) (Short.MAX_VALUE*intensity/100);
		this.duration = duration;
	}

	@Override
	public Sample next() {
		short r = (short) (Math.sin(2*Math.PI*currentTime*freq) * intensity);

		currentTime += Sample.samplePeriod;
		return new Sample(r, r);
	}

	@Override
	public boolean hasNext() {
		if(duration == 0 || currentTime < duration)
			return true;
		else
			return false;
	}
}
