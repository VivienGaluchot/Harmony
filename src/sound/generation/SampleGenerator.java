package sound.generation;

import sound.Sample;

public class SampleGenerator {

	Generator left;
	Generator right;

	public SampleGenerator(Generator left, Generator right) {
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
