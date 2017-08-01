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

public class Linear implements WaveGenerator {

	private double initValue;
	private double endValue;
	private double duration; // second

	private double currentTime; // second

	public Linear(double initValue, double endValue, double duration) {
		this.initValue = initValue;
		this.endValue = endValue;
		this.duration = duration;
		currentTime = 0;
	}

	@Override
	public void reset() {
		currentTime = 0;
	}

	@Override
	public double next() {
		currentTime += Sample.samplePeriod;
		return (currentTime / duration) * (endValue - initValue) + initValue;
	}

	@Override
	public boolean hasNext() {
		if (currentTime < duration)
			return true;
		else
			return false;
	}

}
