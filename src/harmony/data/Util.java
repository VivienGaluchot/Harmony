package harmony.data;

import java.util.HashSet;
import java.util.Set;

public class Util {

	public static boolean containsComputingLoops(DataGenerator processor) {
		Set<DataGenerator> set = new HashSet<>();
		return containsComputingLoops(processor, processor, set);
	}

	public static boolean containsComputingLoops(DataGenerator current, DataGenerator origin,
			Set<DataGenerator> allDependencies) {
		if (current == null || origin == null || allDependencies == null)
			throw new IllegalArgumentException();
		
		// TODO fix bug
		
		if (allDependencies.contains(origin))
			return true;
		
		else {
			if (current instanceof DataProcessor) {
				Set<DataGenerator> localDependencies = ((DataProcessor) current).getDataProcessDependencies();
				if (localDependencies != null) {
					for (DataGenerator dep : localDependencies) {
						if (dep == current)
							return true;
						if (containsComputingLoops(dep, origin, allDependencies))
							return true;
					}
				}
			}
		}
		allDependencies.add(current);
		return false;
	}

}
