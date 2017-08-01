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

import harmony.sound.Sample;
import harmony.sound.generation.WaveGenerator;

public class Sin implements WaveGenerator {
	private WaveGenerator freq;

	private double currAngle;

	public Sin(double freq) {
		this(new Const(freq));
	}

	public Sin(WaveGenerator freq) {
		if (freq == this || freq == null)
			throw new IllegalArgumentException();
		currAngle = 0;
		this.freq = freq;
	}

	@Override
	public void reset() {
		currAngle = 0;
		freq.reset();
	}

	@Override
	public double next() {
		currAngle += Sample.samplePeriod * freq.next();
		currAngle = (float) (currAngle - Math.floor(currAngle));
		return Math.sin(2 * Math.PI * currAngle);
	}

	@Override
	public boolean hasNext() {
		return freq.hasNext();
	}
}
