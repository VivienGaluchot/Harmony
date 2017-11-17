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

package harmony.processcore.data;

public class DataPattern {
	private String[] names;
	private DataType[] types;
	private int size;

	public DataPattern() {
		this.names = null;
		this.types = null;
		this.size = 0;
	}

	public DataPattern(DataType type) {
		this();
		if (type != null) {
			this.names = new String[] { type.toString() };
			this.types = new DataType[] { type };
			this.size = 1;
		}
	}

	public DataPattern(DataType[] types) {
		this();
		if (types != null) {
			this.names = new String[types.length];
			this.types = new DataType[types.length];
			for (int i = 0; i < types.length; i++) {
				assert types[i] != null : "type value can't be null";
				this.names[i] = types[i].toString();
				this.types[i] = types[i];
			}
			this.size = types.length;
		}
	}

	public DataPattern(DataType[] types, String[] names) {
		this();
		assert (types == null && names == null)
				|| (types.length == names.length) : "inconsistent types and names length";
		if (types != null) {
			this.names = new String[names.length];
			this.types = new DataType[types.length];
			for (int i = 0; i < types.length; i++) {
				assert types[i] != null : "type value can't be null";
				this.names[i] = names[i];
				this.types[i] = types[i];
			}
			this.size = types.length;
		}
	}

	public boolean isTypeConsistent(int id, DataType type) {
		return getType(id).includes(type);
	}

	public boolean isValueConsistent(int id, Object value) {
		return types[id].contains(value);
	}

	public boolean includes(DataPattern other) {
		if (other == null)
			return false;
		if (this.size != other.size)
			return false;
		for (int i = 0; i < types.length; i++) {
			if (!types[i].includes(other.types[i]))
				return false;
		}
		return true;
	}

	public int size() {
		return size;
	}

	public Object getNeuter(int id) {
		return types[id].getNeuter();
	}

	public String getName(int i) {
		return names[i];
	}

	public DataType getType(int i) {
		return types[i];
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
