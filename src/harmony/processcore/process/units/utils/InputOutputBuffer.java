package harmony.processcore.process.units.utils;

import harmony.processcore.data.DataArray;
import harmony.processcore.data.DataPattern;
import harmony.processcore.process.DefaultComputeUnit;
import harmony.processcore.process.units.ComputeUnit;

public class InputOutputBuffer extends DefaultComputeUnit {
	
	private DataArray buffer;

	public InputOutputBuffer(DataPattern pattern) {
		super("buffer", null, null);
		buffer = new DataArray(pattern);
	}

	@Override
	public String getName() {
		return "buffer";
	}

	@Override
	public DataPattern getInputPattern() {
		return buffer.getPattern();
	}

	@Override
	public DataPattern getOutputPattern() {
		return buffer.getPattern();
	}

	@Override
	public DataArray compute(DataArray inputValues) {
		buffer.setValues(inputValues);
		return buffer.clone();
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append(getName());
		buff.append(" : ");
		if (getInputPattern() != null)
			buff.append(getInputPattern());
		else
			buff.append('_');
		buff.append(" -> ");
		if (getOutputPattern() != null)
			buff.append(getOutputPattern());
		else
			buff.append('_');
		return buff.toString();
	}

	public Object getValue(int i) {
		return buffer.getValue(i);
	}

}