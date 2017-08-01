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

package harmony.sound.generation.wave;

import harmony.sound.generation.WaveGenerator;

public class Const implements WaveGenerator {

	double value;

	public Const(double value) {
		this.value = value;
	}

	@Override
	public void reset() {

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
