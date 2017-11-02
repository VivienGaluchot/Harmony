package harmony.dataProcess2.process.units;

import harmony.dataProcess2.data.DataArray;
import harmony.dataProcess2.data.DataPattern;
import harmony.dataProcess2.process.ComputeUnit;

public class OutputBuffer extends DataArray implements ComputeUnit {

	public OutputBuffer(DataPattern pattern) {
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
		return new DataPattern();
	}

	@Override
	public DataArray compute(DataArray inputValues) {
		this.setValues(inputValues);
		return new DataArray(null);
	}

}