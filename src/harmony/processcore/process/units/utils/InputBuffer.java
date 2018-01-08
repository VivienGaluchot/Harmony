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
import harmony.processcore.process.DefaultComputeUnit;

public class InputBuffer extends DefaultComputeUnit {
	
	private DataArray buffer;

	public InputBuffer(DataPattern pattern) {
		super("buffer", null, null);
		buffer = new DataArray(pattern);
	}

	@Override
	public DataPattern getOutputPattern() {
		return buffer.getPattern();
	}

	@Override
	public DataArray compute(DataArray inputValues) {
		return buffer.clone();
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

	public void setValues(DataArray inputValues) {
		buffer.setValues(inputValues);
	}

	public void setValue(int i, double d) {
		buffer.setValue(i, d);
	}

}