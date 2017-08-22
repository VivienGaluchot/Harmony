package harmony.data;

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

}
