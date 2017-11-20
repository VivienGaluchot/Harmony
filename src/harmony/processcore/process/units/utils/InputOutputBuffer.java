package harmony.processcore.process.units.utils;

import harmony.processcore.data.DataArray;
import harmony.processcore.data.DataPattern;
import harmony.processcore.process.units.ComputeUnit;

public class InputOutputBuffer extends DataArray implements ComputeUnit {

	public InputOutputBuffer(DataPattern pattern) {
		super(pattern);
	}

	@Override
	public String getName() {
		return "buffer";
	}

	@Override
	public DataPattern getInputPattern() {
		return getPattern();
	}

	@Override
	public DataPattern getOutputPattern() {
		return getPattern();
	}

	@Override
	public DataArray compute(DataArray inputValues) {
		this.setValues(inputValues);
		return this.clone();
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

}