package sound.waveGen;

import sound.Generator;
import sound.Sample;

public class SquareWave implements Generator {
	float freq = 0;

	int current;
	int tresh;

	float currentTime; // second
	short intensity; // absolute value

	public SquareWave(float freq) {
		current = 0;
		currentTime = 0;
		intensity = Short.MAX_VALUE;
		this.freq = freq;
		tresh = (int) (Sample.sampleRate / freq);
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
		return true;
	}
}