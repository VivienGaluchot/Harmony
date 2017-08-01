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

package harmony.sound.generation.mix;

import harmony.sound.generation.WaveGenerator;

public class Repeater extends DurationAdjuster implements WaveGenerator {

	public Repeater(double duration, WaveGenerator source) {
		super(duration, source);
	}

	@Override
	public void reset() {
		super.reset();
	}

	@Override
	public double next() {
		return super.next();
	}

	@Override
	public boolean hasNext() {
		if (!super.hasNext())
			reset();
		return true;
	}

}
