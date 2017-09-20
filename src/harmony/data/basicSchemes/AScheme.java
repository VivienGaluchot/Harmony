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
