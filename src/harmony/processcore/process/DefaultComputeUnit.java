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

package harmony.processcore.process;

import harmony.processcore.data.DataArray;
import harmony.processcore.data.DataPattern;
import harmony.processcore.process.units.ComputeUnit;

public abstract class DefaultComputeUnit implements ComputeUnit {
	
	private static int count = 0;
	
	// info
	private String name;
	private final int id;

	// inputs
	private DataPattern inputPattern;
	// outputs
	private DataPattern outputPattern;
	
	public DefaultComputeUnit(String name, DataPattern inputPattern, DataPattern outputPattern) {
		this.id = count++;
		this.name = name;
		this.inputPattern = inputPattern;
		this.outputPattern = outputPattern;
	}
	
	// ComputeUnit

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public DataPattern getInputPattern() {
		return inputPattern;
	}

	@Override
	public DataPattern getOutputPattern() {
		return outputPattern;
	}

	@Override
	public abstract DataArray compute(DataArray inputValues);

	// Other

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append(getName());
		buff.append("-");
		buff.append(getId());
		buff.append(" : ");
		if (getInputPattern() != null)
			buff.append(getInputPattern());
		else
			buff.append('_');
		buff.append(" -> ");
		if (getOutputPattern() != null)
			buff.append(getOutputPattern());
		else
			buff.append('_');
		return buff.toString();
	}

}
