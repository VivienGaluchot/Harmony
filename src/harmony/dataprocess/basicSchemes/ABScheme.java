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

package harmony.dataprocess.basicSchemes;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import harmony.dataprocess.implem.DataDescriptorModel;
import harmony.dataprocess.model.DataDescriptor;
import harmony.dataprocess.model.DataGenerator;
import harmony.dataprocess.model.ProcessScheme;

public abstract class ABScheme implements ProcessScheme {

	protected DataDescriptor a;
	protected DataDescriptor b;

	public ABScheme() {
		a = new DataDescriptorModel(Double.class, "a");
		b = new DataDescriptorModel(Double.class, "b");
	}

	@Override
	public Class<?> getDataClass() {
		return Double.class;
	}

	@Override
	public Set<DataDescriptor> getDependencies() {
		Set<DataDescriptor> dep = new HashSet<>();
		dep.add(a);
		dep.add(b);
		return dep;
	}

	@Override
	public abstract String getDataName();

	@Override
	public abstract Object process(Map<DataDescriptor, DataGenerator> generatorMap);

	protected double findValue(DataDescriptor des, Map<DataDescriptor, DataGenerator> generatorMap) {
		Object objValue = generatorMap.get(des).getData();
		if (objValue != null)
			return (double) objValue;
		else
			return 0;
	}
}
