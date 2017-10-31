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

import java.util.Set;

import harmony.dataprocess.model.DataGenerator;
import harmony.dataprocess.model.DataProcessor;

public class DataProcessorModel extends DataGeneratorModel implements DataProcessor {

	private Set<DataGenerator> processDependencies;

	public DataProcessorModel() {
		super();
		processDependencies = null;
	}

	public DataProcessorModel(Class<?> dataClass, String name, Object data, Set<DataGenerator> processDependencies) {
		super(dataClass, name, data);
		this.processDependencies = processDependencies;
	}

	public void setDataProcessDependencies(Set<DataGenerator> processDependencies) {
		this.processDependencies = processDependencies;
	}

	@Override
	public Set<DataGenerator> getDataProcessDependencies() {
		return processDependencies;
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		boolean first = true;
		for (DataGenerator gen : processDependencies) {
			if (first)
				first = false;
			else
				buff.append(", ");
			buff.append(gen.getDataName());
		}
		return super.toString() + "(" + buff.toString() + ")";
	}

}
