package sound.waveGen;

import sound.Generator;
import sound.Sample;

public class SinWave implements Generator {
	private float freq = 0;

	private float currAngle; // rad

	public SinWave(float freq) {
		currAngle = 0;
		this.freq = freq;
	}

	@Override
	public Sample next() {
		currAngle += Sample.samplePeriod * freq;
		currAngle = (float) (currAngle - Math.floor(currAngle));
		short r = (short) (Math.sin(2 * Math.PI * currAngle) * Short.MAX_VALUE);
		return new Sample(r, r);
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	public void setFreq(float freq) {
		this.freq = freq;
	}

	public float getFreq() {
		return freq;
	}
}
