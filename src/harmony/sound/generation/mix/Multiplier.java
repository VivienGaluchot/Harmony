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
import harmony.sound.generation.wave.Const;

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
