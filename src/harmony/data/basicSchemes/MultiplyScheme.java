package harmony.data.basicSchemes;

import java.util.Map;

import harmony.data.DataDescriptor;
import harmony.data.DataGenerator;

public class MultiplyScheme extends ABScheme {

	@Override
	public String getDataName() {
		return a.getDataName() + "*" + b.getDataName();
	}

	@Override
	public Object process(Map<DataDescriptor, DataGenerator> generatorMap) {
		System.out.println(findValue(a, generatorMap));
		return findValue(a, generatorMap) * findValue(b, generatorMap);
	}

}
