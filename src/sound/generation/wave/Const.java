package sound.generation.wave;

import sound.generation.Generator;

public class Const implements Generator {
	
	double value;
	
	public Const(double value){
		this.value = value;
	}

	@Override
	public double next() {
		return value;
	}

	@Override
	public boolean hasNext() {
		return true;
	}

}
