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

import harmony.sound.Sample;
import harmony.sound.generation.WaveGenerator;

public class DurationAdjuster implements WaveGenerator {

	private WaveGenerator source;

	private double duration; // second
	private double currentTime; // second

	public DurationAdjuster(double duration, WaveGenerator source) {
		this.source = source;
		this.duration = duration;
		currentTime = 0;
	}

	@Override
	public void reset() {
		currentTime = 0;
		source.reset();
	}

	@Override
	public double next() {
		currentTime += Sample.samplePeriod;
		if (source.hasNext())
			return source.next();
		else
			return 0;
	}

	@Override
	public boolean hasNext() {
		if (currentTime < duration)
			return true;
		else
			return false;
	}

}
