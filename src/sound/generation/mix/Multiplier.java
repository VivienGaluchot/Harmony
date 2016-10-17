package sound.generation.mix;

import sound.generation.Generator;
import sound.generation.wave.Const;

public class Multiplier implements Generator {

	private Generator source;
	
	private Generator volume;
	
	public Multiplier(double volume, Generator source) {
		this(new Const(volume), source);
	}

	public Multiplier(Generator volume, Generator source) {
		this.source = source;
		this.volume = volume;
	}

	@Override
	public double next() {
		return source.next() * volume.next();
	}

	@Override
	public boolean hasNext() {
		return source.hasNext() && volume.hasNext();
	}
}
