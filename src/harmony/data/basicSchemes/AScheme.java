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

package harmony.data.basicSchemes;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import harmony.data.DataDescriptor;
import harmony.data.DataDescriptorModel;
import harmony.data.DataGenerator;
import harmony.data.ProcessScheme;

public abstract class AScheme implements ProcessScheme {

	protected DataDescriptor a;

	public AScheme() {
		a = new DataDescriptorModel(Double.class, "a");
	}

	@Override
	public Class<?> getDataClass() {
		return Double.class;
	}

	@Override
	public Set<DataDescriptor> getDependencies() {
		Set<DataDescriptor> dep = new HashSet<>();
		dep.add(a);
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
