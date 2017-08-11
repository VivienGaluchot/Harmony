package harmony.data;

import java.util.HashSet;
import java.util.Set;

public class Util {

	public static boolean containsComputingLoops(DataProcessor processor) {
		Set<DataProcessor> set = new HashSet<>();
		return containsComputingLoops(processor, set);
	}

	public static boolean containsComputingLoops(DataProcessor processor, Set<DataProcessor> allDependencies) {
		if (allDependencies.contains(processor))
			return true;
		else {
			Set<DataProcessor> localDep = processor.getDataProcessDependencies();
			if (localDep != null) {
				for (DataProcessor ip : localDep) {
					if (containsComputingLoops(ip, allDependencies))
						return true;
				}
			}
		}
		allDependencies.add(processor);
		return false;
	}

}
