package harmony.dataProcess2.process.units;

import harmony.dataProcess2.data.DataArray;
import harmony.dataProcess2.data.DataPattern;
import harmony.dataProcess2.process.ComputeUnit;

public class InputBuffer extends DataArray implements ComputeUnit {

	public InputBuffer(DataPattern pattern) {
		super(pattern);
	}

	@Override
	public String getName() {
		return "buffer";
	}

	@Override
	public DataPattern getInputPattern() {
		return null;
	}

	@Override
	public DataPattern getOutputPattern() {
		return getPattern();
	}

	@Override
	public DataArray compute(DataArray inputValues) {
		return this.clone();
	}

}