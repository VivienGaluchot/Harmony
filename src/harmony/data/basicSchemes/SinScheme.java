package harmony.data.basicSchemes;

import java.util.Map;

import harmony.data.DataDescriptor;
import harmony.data.DataGenerator;

public class SinScheme extends AScheme {

	@Override
	public String getDataName() {
		return "sin(" + a.getDataName() + ")";
	}

	@Override
	public Object process(Map<DataDescriptor, DataGenerator> generatorMap) {
			return Math.sin(findValue(a, generatorMap));
	}

}
