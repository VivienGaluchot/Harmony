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

package harmony.dataProcess2;

public class DataArray {
	private Object[] values;
	private DataPattern pattern;

	public DataArray(DataPattern pattern) {
		if (pattern == null)
			throw new NullPointerException("pattern can't be null");
		
		this.pattern = pattern;
		
		values = new Object[pattern.size()];
		for (int i = 0 ; i < pattern.size(); i++) {
			values[i] = pattern.getType(i).getNeuter();
		}
	}

	public void setValue(int id, Object value) {
		if (!pattern.isValid(id, value))
			throw new IllegalArgumentException("wrong value type");

		values[id] = value;
	}

	public Object getValue(int i) {
		return values[i];
	}

	public int size() {
		return values.length;
	}
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append('[');
		if (this.size() > 0) {
			if (values[0] != null)
				buff.append(values[0].toString());
			else
				buff.append('_');
			for (int i = 1; i < values.length; i++) {
				buff.append(", ");
				if (values[i] != null)
					buff.append(values[i].toString());
				else
					buff.append('_');
			}
		}
		buff.append(']');
		return buff.toString();
	}
}
