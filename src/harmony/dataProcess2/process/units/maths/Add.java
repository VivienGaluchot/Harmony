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

package harmony.dataProcess2.process.units.maths;

import harmony.dataProcess2.data.DataArray;
import harmony.dataProcess2.data.DataPattern;
import harmony.dataProcess2.data.DataType;
import harmony.dataProcess2.data.DataTypes;
import harmony.dataProcess2.process.ComputeUnit;

public class Add implements ComputeUnit {

	private DataPattern inPattern;
	private DataPattern outPattern;

	public Add() {
		inPattern = new DataPattern(new DataType[] { DataTypes.Double, DataTypes.Double });
		outPattern = new DataPattern(new DataType[] { DataTypes.Double });
	}

	@Override
	public String getName() {
		return "add";
	}

	@Override
	public DataPattern getInputPattern() {
		return inPattern;
	}

	@Override
	public DataPattern getOutputPattern() {
		return outPattern;
	}

	@Override
	public DataArray compute(DataArray inputValues) {
		DataArray da = new DataArray(getOutputPattern());
		Double a = (Double) inputValues.getValue(0);
		Double b = (Double) inputValues.getValue(1);
		da.setValue(0, a + b);
		return da;
	}

}
