package sound.generation.mix;

import java.util.ArrayList;

import sound.generation.Generator;

/**
 * Allow to add generic generators
 * 
 * If one generator have end, the waveAdder will continue if there is one or
 * more generator sill playing. Stopped generators will be removed from the list
 * 
 * @author Vivien
 */
public class Adder extends ArrayList<Generator>implements Generator {
	private static final long serialVersionUID = 1L;

	@Override
	synchronized public boolean add(Generator g) {
		return super.add(g);
	}

	@Override
	synchronized public double next() {
		double s = 0;
		for (Generator g : this) {
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

		ArrayList<Generator> toRemove = new ArrayList<>();

		for (Generator g : this) {
			if (g != null && g.hasNext()) {
				hasNext = true;
			} else {
				toRemove.add(g);
			}
		}

		this.removeAll(toRemove);

		return hasNext;
	}

}
