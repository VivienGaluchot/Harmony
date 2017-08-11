package harmony.data;

import java.util.Set;

public interface DataProcessor {
	
	public Object processData();

	public Class<?> getDataClass();
	
	public Set<DataProcessor> getDataProcessDependencies();
}
