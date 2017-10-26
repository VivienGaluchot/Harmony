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

package harmony.dataprocess.implem;

import harmony.dataprocess.model.ComputeUnit;

public class AtomicProcess<T> {

	// inputs
	private AtomicProcess<?>[] dependencies;

	// output
	private String name;
	private boolean valuated;
	private T value;
	private Class<T> valueClass;

	// compute
	private ComputeUnit<T> computeUnit;

	public AtomicProcess(String name, Class<T> valueClass, ComputeUnit<T> computeUnit) {
		if (name == null)
			throw new NullPointerException("name can't be null");
		if (valueClass == null)
			throw new NullPointerException("valueClass can't be null");
		if (computeUnit == null)
			throw new NullPointerException("computeUnit can't be null");

		this.name = name;
		this.valueClass = valueClass;
		this.computeUnit = computeUnit;

		dependencies = new AtomicProcess<?>[getDependenciesNumber()];
		valuated = false;
	}

	public void setDependencie(int id, AtomicProcess<?> process) {
		if (process == null)
			throw new NullPointerException("process can't be null");

		Class<?>[] inputClassPattern = this.computeUnit.getInputsClassPattern();
		if (inputClassPattern[id] != process.valueClass)
			throw new IllegalArgumentException("wrong class type");

		if (process.isInDependenciesTree(this))
			throw new IllegalArgumentException("dependencies boucle detected");

		dependencies[id] = process;
	}

	public T getValue() {
		if (!valuated) {
			Object[] inputValues = new Object[getDependenciesNumber()];
			for (int i = 0; i < inputValues.length; i++) {
				if (dependencies[i] != null) {
					inputValues[i] = dependencies[i].getValue();
				} else {
					inputValues[i] = null;
				}
			}
			value = computeUnit.computeValue(inputValues);
			// TODO implement lasy reevaluation
			// valuated = true;
		}
		return value;
	}

	public boolean isInDependenciesTree(AtomicProcess<?> process) {
		for (AtomicProcess<?> directDep : dependencies) {
			if (directDep != null) {
				if (directDep == process) {
					return true;
				} else {
					return directDep.isInDependenciesTree(process);
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
		buff.append('(');
		buff.append(valueClass.getSimpleName());
		buff.append(") ");
		buff.append(name);
		if (dependencies.length > 0) {
			buff.append('(');
			if (dependencies[0] != null)
				buff.append(dependencies[0].name);
			else
				buff.append('_');
			for (int i = 1; i < dependencies.length; i++) {
				buff.append(", ");
				if (dependencies[i] != null)
					buff.append(dependencies[i].name);
				else
					buff.append('_');
			}
			buff.append(')');
		}
		buff.append(" = ");
		Object value = getValue();
		if (value != null) {
			buff.append(value.toString());
		} else {
			buff.append('_');
		}
		return buff.toString();
	}

	private int getDependenciesNumber() {
		Class<?>[] classPattern = this.computeUnit.getInputsClassPattern();
		if (classPattern != null)
			return this.computeUnit.getInputsClassPattern().length;
		else
			return 0;
	}
}
