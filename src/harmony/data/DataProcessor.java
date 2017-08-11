package harmony.data;

import java.util.Set;

public interface DataProcessor extends DataGenerator {

	public Set<DataProcessor> getDataProcessDependencies();

}
