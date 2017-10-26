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

package harmony.dataprocess;

import java.util.HashSet;
import java.util.Set;

import harmony.dataprocess.implem.AtomicProcess;
import harmony.dataprocess.implem.DataGeneratorModel;
import harmony.dataprocess.implem.DataProcessorModel;
import harmony.dataprocess.model.ComputeUnit;
import harmony.dataprocess.model.DataGenerator;
import harmony.dataprocess.model.DataProcessor;

public class DataProcessTest {
	public static void main(String[] args) throws Exception {
		DataGenerator a = new DataGeneratorModel(Double.class, "a", new Double(1.0));
		DataGenerator b = new DataGeneratorModel(Double.class, "b", new Double(1.0));
		System.out.println(a);
		System.out.println(b);
		
		Set<DataGenerator> dep = new HashSet<DataGenerator>();
		dep.add(a);
		dep.add(b);
		DataProcessor pr = new DataProcessorModel(Double.class, "b", new Double(1.0), dep);
		System.out.println(pr);
		
		AtomicProcess<Double> c15 = new AtomicProcess<Double>("c15", Double.class, new ComputeUnit<Double>() {
			@Override
			public Class<?>[] getInputsClassPattern() { return null; }

			@Override
			public Double computeValue(Object[] inputValues) {
				return 15.0;
			}
		});
		System.out.println(c15);
		AtomicProcess<Double> c17 = new AtomicProcess<Double>("c17", Double.class, new ComputeUnit<Double>() {
			@Override
			public Class<?>[] getInputsClassPattern() { return null; }

			@Override
			public Double computeValue(Object[] inputValues) {
				return 17.0;
			}
		});
		System.out.println(c17);
		AtomicProcess<Double> add = new AtomicProcess<Double>("add", Double.class, new ComputeUnit<Double>() {
			@Override
			public Class<?>[] getInputsClassPattern() {
				return new Class<?>[]{Double.class, Double.class};
			}

			@Override
			public Double computeValue(Object[] inputValues) {
				Double a = (Double) inputValues[0];
				if (a == null)
					a = 0.0;
				Double b = (Double) inputValues[1];
				if (b == null)
					b = 0.0;
				return a + b;
			}
		});
		System.out.println(add);
		add.setDependencie(0, c15);
		System.out.println(add);
		add.setDependencie(1, c17);
		System.out.println(add);
		add.setDependencie(0, c17);
		System.out.println(add);
	}
}
