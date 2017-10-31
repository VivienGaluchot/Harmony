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

package harmony.dataProcess2.data;

public class DataPattern {
	private DataType[] types;
	private int size;

	public DataPattern() {
		this(null);
	}

	public DataPattern(DataType[] types) {
		if (types != null) {
			this.types = types;
			this.size = types.length;
		} else {
			this.types = null;
			this.size = 0;
		}
	}

	public boolean isValid(int id, Object value) {
		return value.getClass() == types[id].getValueClass();
	}

	public DataType getType(int i) {
		return types[i];
	}

	public int size() {
		return size;
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append('(');
		if (this.size() > 0) {
			if (types[0] != null)
				buff.append(types[0].toString());
			else
				buff.append('_');
			for (int i = 1; i < types.length; i++) {
				buff.append(", ");
				if (types[i] != null)
					buff.append(types[i].toString());
				else
					buff.append('_');
			}
		}
		buff.append(')');
		return buff.toString();
	}
}
