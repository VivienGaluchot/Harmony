package sound.generation.mix;

import sound.generation.WaveGenerator;
import sound.generation.wave.Const;

public class Multiplier implements WaveGenerator {

	private WaveGenerator source;

	private WaveGenerator volume;

	public Multiplier(double volume, WaveGenerator source) {
		this(new Const(volume), source);
	}

	public Multiplier(WaveGenerator volume, WaveGenerator source) {
		if (source == this || source == null)
			throw new IllegalArgumentException();
		this.source = source;
		this.volume = volume;
	}

	@Override
	public void reset() {
		source.reset();
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
