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

package harmony.dataProcess2.process;

import harmony.dataProcess2.data.DataArray;
import harmony.dataProcess2.data.DataPattern;

public class ComplexProcess implements ComputeUnit {
	// info
	private String name;
	
	// inputs
	private DataPattern inputPattern;
	
	// outputs
	private DataPattern outputPattern;
	
	public ComplexProcess(String name, DataPattern inputPattern, DataPattern outputPattern) {
		this.name = name;
		this.inputPattern = inputPattern;
		this.outputPattern = outputPattern;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public DataPattern getInputPattern() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataPattern getOutputPattern() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataArray compute(DataArray inputValues) {
		// TODO Auto-generated method stub
		return null;
	}
	// TODO
}
