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

package harmony.data;

import java.util.HashSet;
import java.util.Set;

public class Util {

	/**
	 * Test if given generator depends on descriptor
	 * 
	 * @param generator
	 * @param origin
	 * @return
	 */
	public static boolean isInDependenciesTree(DataGenerator generator, DataDescriptor descriptor) {
		if (generator == null || descriptor == null)
			throw new IllegalArgumentException();

		if (generator instanceof DataProcessor) {
			Set<DataGenerator> localDependencies = ((DataProcessor) generator).getDataProcessDependencies();
			if (localDependencies != null) {
				for (DataGenerator dep : localDependencies) {
					if (dep.equals(descriptor) || isInDependenciesTree(dep, descriptor))
						return true;
				}
			}
		}

		return false;
	}

	public static Set<DataDescriptor> getDependencies(DataProcessor processor) {
		Set<DataDescriptor> dependencies = new HashSet<DataDescriptor>();
		getDependencies(processor, dependencies);
		return dependencies;
	}

	public static void getDependencies(DataProcessor processor, Set<DataDescriptor> dependencies) {
		if (processor == null || dependencies == null)
			throw new IllegalArgumentException();

		Set<DataGenerator> localDependencies = ((DataProcessor) processor).getDataProcessDependencies();
		if (localDependencies != null) {
			dependencies.addAll(localDependencies);
			for (DataGenerator dep : localDependencies) {
				if (dep instanceof DataProcessor)
					getDependencies((DataProcessor) dep, dependencies);
			}
		}
	}
}
