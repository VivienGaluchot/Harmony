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

package harmony.processcore.process.units.maths;

import harmony.processcore.data.DataArray;
import harmony.processcore.data.DataPattern;
import harmony.processcore.data.DataType;
import harmony.processcore.process.ComputeUnit;

public class Constant implements ComputeUnit {

	private DataArray dataArray;
	private DataPattern outPattern;

	public Constant(DataType type, Object value) {
		outPattern = new DataPattern(type);
		dataArray = new DataArray(outPattern);
		dataArray.setValue(0, value);
	}

	@Override
	public String getName() {
		return "constant";
	}

	@Override
	public DataPattern getInputPattern() {
		return null;
	}

	@Override
	public DataPattern getOutputPattern() {
		return outPattern;
	}

	@Override
	public DataArray compute(DataArray inputValues) {
		return dataArray;
	}

}
