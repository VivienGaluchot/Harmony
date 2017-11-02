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
		this.types = null;
		this.size = 0;
	}

	public DataPattern(DataType type) {
		if (type != null) {
			this.types = new DataType[] { type };
			this.size = 1;
		} else {
			this.types = null;
			this.size = 0;
		}
	}

	public DataPattern(DataType[] types) {
		if (types != null) {
			for (DataType dt : types) {
				assert dt != null : "type value can't be null";
			}
			this.types = types;
			this.size = types.length;
		} else {
			this.types = null;
			this.size = 0;
		}
	}

	public boolean isTypeConsistent(int id, DataType type) {
		return type.equals(types[id]);
	}

	public boolean isValueConsistent(int id, Object value) {
		return types[id].contains(value);
	}

	public DataType getType(int i) {
		return types[i];
	}

	public int size() {
		return size;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return this.size() == 0;
		if (!(o instanceof DataPattern))
			return false;
		DataPattern other = (DataPattern) o;

		if (other.size() != size())
			return false;

		for (int i = 0; i < size(); i++)
			if (!getType(i).equals(other.getType(i)))
				return false;

		return true;
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
