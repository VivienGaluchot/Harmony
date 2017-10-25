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

package harmony.dataprocess.implem;

import harmony.dataprocess.model.DataDescriptor;

public class DataDescriptorModel implements DataDescriptor {

	private Class<?> dataClass;
	private String name;

	public DataDescriptorModel() {
		this.dataClass = null;
		this.name = null;
	}
	
	public DataDescriptorModel(Class<?> dataClass, String name) {
		this.dataClass = dataClass;
		this.name = name;
	}
	
	public void setDataClass(Class<?> dataClass) {
		this.dataClass = dataClass;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Class<?> getDataClass() {
		return dataClass;
	}

	@Override
	public String getDataName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "(" + this.getDataClass().getSimpleName() + ") " + this.getDataName();
	}

}
