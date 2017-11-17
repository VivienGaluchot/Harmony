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

package harmony.processcore.process.units.utils;

import harmony.processcore.data.DataArray;
import harmony.processcore.data.DataPattern;
import harmony.processcore.process.units.ComputeUnit;

public class OutputBuffer extends DataArray implements ComputeUnit {

	public OutputBuffer(DataPattern pattern) {
		super(pattern);
	}

	@Override
	public String getName() {
		return "buffer";
	}

	@Override
	public DataPattern getInputPattern() {
		return getPattern();
	}

	@Override
	public DataPattern getOutputPattern() {
		return null;
	}

	@Override
	public DataArray compute(DataArray inputValues) {
		this.setValues(inputValues);
		return null;
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append(getName());
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