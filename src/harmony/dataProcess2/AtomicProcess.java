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

package harmony.dataProcess2;

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
		if (name == null)
			throw new NullPointerException("name can't be null");
		if (computeUnit == null)
			throw new NullPointerException("computeUnit can't be null");

		this.name = name;
		this.computeUnit = computeUnit;

		DataPattern inputPattern = computeUnit.getInputPattern();
		if (inputPattern != null) {
			inputDependencies = new Dependencie[inputPattern.size()];
		} else {
			inputDependencies = new Dependencie[0];
		}

		values = new DataArray(computeUnit.getOutputPattern());
		valuated = false;
	}

	public void setDependencie(int inputId, AtomicProcess process) {
		this.setDependencie(inputId, process, 0);
	}

	public void setDependencie(int inputId, AtomicProcess process, int processOutputId) {
		if (process != null) {
			DataPattern inputClassPattern = this.computeUnit.getInputPattern();
			if (inputClassPattern.getType(inputId) != process.getOutputPattern().getType(processOutputId))
				throw new IllegalArgumentException("wrong class type");
			if (process.isInDependenciesTree(this))
				throw new IllegalArgumentException("dependencie detected");
			inputDependencies[inputId] = new Dependencie(process, processOutputId);
		} else {
			inputDependencies[inputId] = null;
		}
	}

	public DataArray getValues() {
		if (!valuated) {
			DataPattern inputClassPattern = this.computeUnit.getInputPattern();
			DataArray inputValues = new DataArray(inputClassPattern);
			for (int i = 0; i < inputValues.size(); i++) {
				if (inputDependencies[i] != null) {
					inputValues.setValue(i, inputDependencies[i].getValue());
				}
			}
			values = computeUnit.compute(inputValues);
			// TODO implement lazy reevaluation
			// valuated = true;
		}
		return values;
	}

	public Object getValue(int i) {
		if(valuated)
			return values.getValue(i);
		else
			return getValues().getValue(i);
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

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append(getName());
		buff.append(" = ");
		buff.append(computeUnit.getName());
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
		return computeUnit.compute(inputValues);
	}
}
