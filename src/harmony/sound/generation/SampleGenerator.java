package harmony.sound.generation;

import harmony.sound.Sample;

public class SampleGenerator {

	WaveGenerator left;
	WaveGenerator right;

	public SampleGenerator(WaveGenerator left, WaveGenerator right) {
		this.left = left;
		this.right = right;
	}

	public Sample next() {
		return new Sample(left.next(), right.next());
	}

	public boolean hasNext() {
		return left.hasNext() && right.hasNext();
	}
}
