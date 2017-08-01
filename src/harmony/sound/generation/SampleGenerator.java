//    Harmony : procedural sound waves generator
//    Copyright (C) 2017  Vivien Galuchot
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, version 3 of the License.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

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
