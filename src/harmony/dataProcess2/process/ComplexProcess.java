//    Harmony : procedural sound waves generator
//    Copyright (C) 2017  Vivien Galuchot
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, version 3 of the License.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

package harmony.dataProcess2.process;

import harmony.dataProcess2.data.DataArray;
import harmony.dataProcess2.data.DataPattern;
import harmony.dataProcess2.process.units.InputBuffer;
import harmony.dataProcess2.process.units.OutputBuffer;

public class ComplexProcess implements ComputeUnit {
	// info
	private String name;

	// inputs
	private DataPattern inputPattern;
	private InputBuffer inputBuffer;
	private AtomicProcess inputProcess;

	// outputs
	private DataPattern outputPattern;
	private OutputBuffer outputBuffer;
	private AtomicProcess outputProcess;

	public ComplexProcess(String name, DataPattern inputPattern, DataPattern outputPattern) {
		this.name = name;
		this.inputPattern = inputPattern;
		inputBuffer = new InputBuffer(inputPattern);
		inputProcess = new AtomicProcess("input", inputBuffer);
		this.outputPattern = outputPattern;
		outputBuffer = new OutputBuffer(outputPattern);
		outputProcess = new AtomicProcess("output", outputBuffer);
	}

	// Allows to build-up intern compute process

	public AtomicProcess getInputProcess() {
		return inputProcess;
	}

	public AtomicProcess getOutputProcess() {
		return outputProcess;
	}

	// ComputeUnit, extern access

	@Override
	public String getName() {
		return name;
	}

	@Override
	public DataPattern getInputPattern() {
		return inputPattern;
	}

	@Override
	public DataPattern getOutputPattern() {
		return outputPattern;
	}

	@Override
	public DataArray compute(DataArray inputValues) {
		assert inputValues == null
				|| inputValues.getPattern().equals(getInputPattern()) : "inconsistent input values type";

		// push input values in input buffer
		inputBuffer.setValues(inputValues);
		// now inputProcess can be user and contain inputValues

		// excecute
		outputProcess.getValues();

		// extract values from output buffer
		DataArray outputValues = outputBuffer.clone();

		assert outputValues.getPattern().equals(getOutputPattern()) : "inconsistent output values type";
		return outputValues;
	}

	// Other

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append(getName());
		// TODO
		return buff.toString();
	}
}
