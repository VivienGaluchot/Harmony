package sound.generation.mix;

import java.util.ArrayList;

import sound.generation.WaveGenerator;

/**
 * Allow to add generic generators
 * 
 * If one generator have end, the waveAdder will continue if there is one or
 * more generator sill playing. Stopped generators will be removed from the list
 * 
 * @author Vivien
 */
public class Adder extends ArrayList<WaveGenerator> implements WaveGenerator {
	private static final long serialVersionUID = 1L;

	@Override
	synchronized public boolean add(WaveGenerator g) {
		if (g == this || g == null)
			throw new IllegalArgumentException();
		return super.add(g);
	}

	@Override
	public void reset() {
		for (WaveGenerator g : this) {
			g.reset();
		}
	}

	@Override
	synchronized public double next() {
		double s = 0;
		for (WaveGenerator g : this) {
			if (g.hasNext())
				s += g.next();
		}
		return s;
	}

	/**
	 * Return true if there is at least one Generator playing. When a generator
	 * stops, it is removed from the list
	 */
	@Override
	synchronized public boolean hasNext() {
		boolean hasNext = false;

		ArrayList<WaveGenerator> toRemove = new ArrayList<>();

		for (WaveGenerator g : this) {
			if (g.hasNext()) {
				hasNext = true;
			} else {
				toRemove.add(g);
			}
		}

		this.removeAll(toRemove);

		return hasNext;
	}

}
