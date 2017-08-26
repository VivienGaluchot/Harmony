package harmony.data;

public class DataDescriptorModel implements DataDescriptor {

	Class<?> dataClass;
	String name;

	public DataDescriptorModel(Class<?> dataClass, String name) {
		this.dataClass = dataClass;
		this.name = name;
	}

	@Override
	public Class<?> getDataClass() {
		return dataClass;
	}

	@Override
	public String getDataName() {
		return name;
	}

}
