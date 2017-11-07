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

public class SimpleDataType implements DataType {

	private Class<?> valueClass;
	private Object neuter;

	public SimpleDataType(Class<?> valueClass, Object neuter) {
		this.valueClass = valueClass;
		this.neuter = neuter;
	}

	public Class<?> getValueClass() {
		return valueClass;
	}

	public Object getNeuter() {
		return neuter;
	}

	public boolean contains(Object value) {
		return value.getClass().equals(getValueClass());
	}

	@Override
	public boolean includes(DataType type) {
		return this.equals(type);
	}

	@Override
	public String toString() {
		return valueClass.getSimpleName();
	}
}
