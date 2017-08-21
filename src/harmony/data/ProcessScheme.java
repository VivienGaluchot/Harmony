package harmony.data;

import java.util.Map;
import java.util.Set;

public interface ProcessScheme extends DataDescriptor {

	public Set<DataDescriptor> getDependencies();

	public Object process(Map<DataDescriptor, DataGenerator> generatorMap);
	
}
