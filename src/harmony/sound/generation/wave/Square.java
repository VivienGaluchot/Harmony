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

public class Square implements WaveGenerator {
	private WaveGenerator freq;

	private int counter;
	private double output;

	public Square(double freq) {
		this(new Const(freq));
	}

	public Square(WaveGenerator freq) {
		if (freq == this || freq == null)
			throw new IllegalArgumentException();
		counter = 0;
		output = 1;
		this.freq = freq;
	}

	@Override
	public void reset() {
		counter = 0;
		output = 1;
		freq.reset();
	}

	@Override
	public double next() {
		if (counter > Sample.sampleRate / freq.next()) {
			output = (short) (-1 * output);
			counter = 0;
		}
		counter++;
		return output;
	}

	@Override
	public boolean hasNext() {
		return freq.hasNext();
	}
}