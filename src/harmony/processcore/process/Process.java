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

package harmony.processcore.process;

import harmony.processcore.data.DataArray;
import harmony.processcore.data.DataPattern;

public class Process implements ComputeUnit {
	// info
	private String name;

	// inputs
	private ProcessOutput[] inputDependencies;

	// compute
	private ComputeUnit computeUnit;

	// output
	private boolean valuated;
	private DataArray values;

	public Process(String name, ComputeUnit computeUnit) {
		assert name != null : "name can't be null";
		assert computeUnit != null : "computeUnit can't be null";

		this.name = name;
		this.computeUnit = computeUnit;

		DataPattern inputPattern = getInputPattern();
		if (inputPattern != null) {
			inputDependencies = new ProcessOutput[inputPattern.size()];
		} else {
			inputDependencies = new ProcessOutput[0];
		}

		values = new DataArray(getOutputPattern());
		valuated = false;
	}

	// Dependencies

	public ProcessOutput getOutput(int outputId) {
		return new ProcessOutput(this, outputId);
	}

	public void resetDependencie(int inputId) {
		this.setDependencie(inputId, null);
	}

	public void setDependencie(int inputId, ProcessOutput dependencie) {
		if (dependencie != null) {
			DataPattern inputClassPattern = getInputPattern();
			if (inputClassPattern == null || !inputClassPattern.isTypeConsistent(inputId, dependencie.getOutputType()))
				throw new IllegalArgumentException("inconsistent class type");
			if (dependencie.getProcess().isInDependenciesTree(this))
				throw new IllegalArgumentException("dependencie detected");
			inputDependencies[inputId] = dependencie;
		} else {
			inputDependencies[inputId] = null;
		}
	}

	public ProcessOutput getDependencie(int inputId) {
		return inputDependencies[inputId];
	}

	public boolean isInDependenciesTree(Process process) {
		if (process != null) {
			if (process == this) {
				return true;
			} else {
				for (ProcessOutput directDep : inputDependencies) {
					if (directDep != null)
						return directDep.getProcess().isInDependenciesTree(process);
				}
			}
		}
		return false;
	}

	// Values

	public DataArray getValues() {
		if (!valuated) {
			DataArray inputValues = null;
			DataPattern inputClassPattern = getInputPattern();
			if (inputClassPattern != null) {
				inputValues = new DataArray(inputClassPattern);
				for (int i = 0; i < inputValues.size(); i++) {
					if (inputDependencies[i] != null) {
						inputValues.setValue(i, inputDependencies[i].getValue());
					}
				}
			}
			values = compute(inputValues);
			// TODO implement lazy reevaluation
			// valuated = true;
		}
		return values;
	}

	public Object getValue(int i) {
		if (valuated)
			return values.getValue(i);
		else
			return getValues().getValue(i);
	}

	// ComputeUnit

	public ComputeUnit getComputeUnit() {
		return computeUnit;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public DataPattern getInputPattern() {
		return computeUnit.getInputPattern();
	}

	@Override
	public DataPattern getOutputPattern() {
		return computeUnit.getOutputPattern();
	}

	@Override
	public DataArray compute(DataArray inputValues) {
		assert inputValues == null
				|| inputValues.getPattern().equals(computeUnit.getInputPattern()) : "inconsistent input values type";
		DataArray outputValues = computeUnit.compute(inputValues);
		assert outputValues == null
				|| outputValues.getPattern().equals(computeUnit.getOutputPattern()) : "inconsistent output values type";
		return outputValues;
	}

	// Other

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append(getName());
		buff.append(" = ");
		buff.append(getName());
		if (inputDependencies.length > 0) {
			buff.append('(');
			if (inputDependencies[0] != null)
				buff.append(inputDependencies[0]);
			else
				buff.append('_');
			for (int i = 1; i < inputDependencies.length; i++) {
				buff.append(", ");
				if (inputDependencies[i] != null)
					buff.append(inputDependencies[i]);
				else
					buff.append('_');
			}
			buff.append(')');
		}
		return buff.toString();
	}
}
