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

import harmony.dataprocess.model.DataGenerator;

public class DataGeneratorModel extends DataDescriptorModel implements DataGenerator {
	
	private Object data;

	public DataGeneratorModel() {
		super();
		data = null;
	}
	
	public DataGeneratorModel(Class<?> dataClass, String name, Object data) {
		super(dataClass, name);
		this.data = data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public Object getData() {
		return data;
	}
	
	@Override
	public String toString() {
		return super.toString() + " = " + data;
	}

}
