package harmony.data.basicSchemes;

import java.util.Map;

import harmony.data.DataDescriptor;
import harmony.data.DataGenerator;

public class AddScheme extends ABScheme {

	@Override
	public String getDataName() {
		return a.getDataName() + "+" + b.getDataName();
	}

	@Override
	public Object process(Map<DataDescriptor, DataGenerator> generatorMap) {
		return findValue(a, generatorMap) + findValue(b, generatorMap);
	}

}
