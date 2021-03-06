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
import harmony.processcore.process.DefaultComputeUnit;

public class Constant extends DefaultComputeUnit {
	private DataArray dataArray;

	public Constant(DataType type, Object value) {
		super("[" + value.toString() + "]", null, new DataPattern(type));
		dataArray = new DataArray(getOutputPattern());
		dataArray.setValue(0, value);
	}

	@Override
	public DataArray compute(DataArray inputValues) {
		return dataArray;
	}

}