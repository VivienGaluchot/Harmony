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

public class AtomicProcess implements ComputeUnit {
	// info
	private String name;

	// inputs
	private Dependencie[] inputDependencies;

	// compute
	private ComputeUnit computeUnit;

	// output
	private boolean valuated;
	private DataArray values;

	public AtomicProcess(String name, ComputeUnit computeUnit) {
		assert name != null : "name can't be null";
		assert computeUnit != null : "computeUnit can't be null";

		this.name = name;
		this.computeUnit = computeUnit;

		DataPattern inputPattern = getInputPattern();
		if (inputPattern != null) {
			inputDependencies = new Dependencie[inputPattern.size()];
		} else {
			inputDependencies = new Dependencie[0];
		}

		values = new DataArray(getOutputPattern());
		valuated = false;
	}

	// Dependencies

	public void setDependencie(int inputId, AtomicProcess process) {
		this.setDependencie(inputId, process, 0);
	}

	public void setDependencie(int inputId, AtomicProcess process, int processOutputId) {
		if (process != null) {
			DataPattern inputClassPattern = getInputPattern();
			if (inputClassPattern == null || !inputClassPattern.isTypeConsistent(inputId,
					process.getOutputPattern().getType(processOutputId)))
				throw new IllegalArgumentException("inconsistent class type");
			if (process.isInDependenciesTree(this))
				throw new IllegalArgumentException("dependencie detected");
			inputDependencies[inputId] = new Dependencie(process, processOutputId);
		} else {
			inputDependencies[inputId] = null;
		}
	}

	public Dependencie getDependencie(int inputId) {
		return inputDependencies[inputId];
	}

	public boolean isInDependenciesTree(AtomicProcess process) {
		if (process != null) {
			if (process == this) {
				return true;
			} else {
				for (Dependencie directDep : inputDependencies) {
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
