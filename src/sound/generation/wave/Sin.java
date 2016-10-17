package sound.generation.wave;

import sound.Sample;
import sound.generation.Generator;

public class Sin implements Generator {
	private Generator freq;

	private double currAngle;

	public Sin(double freq) {
		this(new Const(freq));
	}
	
	public Sin(Generator freq){
		currAngle = 0;
		this.freq = freq;		
	}

	@Override
	public double next() {
		currAngle += Sample.samplePeriod * freq.next();
		currAngle = (float) (currAngle - Math.floor(currAngle));
		return Math.sin(2 * Math.PI * currAngle);
	}

	@Override
	public boolean hasNext() {
		return freq.hasNext();
	}
}
