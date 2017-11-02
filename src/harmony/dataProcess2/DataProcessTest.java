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

import harmony.dataProcess2.data.DataArray;
import harmony.dataProcess2.data.DataPattern;
import harmony.dataProcess2.data.DataType;
import harmony.dataProcess2.process.AtomicProcess;
import harmony.dataProcess2.process.ComplexProcess;
import harmony.dataProcess2.process.ComputeUnit;
import harmony.dataProcess2.process.units.InputBuffer;
import harmony.dataProcess2.process.units.OutputBuffer;
import harmony.dataProcess2.process.units.Constant;
import harmony.dataProcess2.process.units.operators.Sub;
import harmony.dataProcess2.process.units.operators.Add;

public class DataProcessTest {
	public static void main(String[] args) throws Exception {
		AtomicProcess consts = new AtomicProcess("consts", new ComputeUnit() {
			@Override
			public String getName() {
				return "c";
			}

			@Override
			public DataPattern getInputPattern() {
				return null;
			}

			@Override
			public DataPattern getOutputPattern() {
				return new DataPattern(new DataType[] { DataType.Double, DataType.Double });
			}

			@Override
			public DataArray compute(DataArray inputValues) {
				DataArray da = new DataArray(getOutputPattern());
				da.setValue(0, 15.0);
				da.setValue(1, 20.0);
				return da;
			}
		});
		System.out.println(consts);
		System.out.println(consts.getInputPattern());
		System.out.println(consts.getOutputPattern());
		System.out.println(consts.getValues());

		ComputeUnit addUnit = new ComputeUnit() {
			@Override
			public String getName() {
				return "add";
			}

			@Override
			public DataPattern getInputPattern() {
				return new DataPattern(new DataType[] { DataType.Double, DataType.Double });
			}

			@Override
			public DataPattern getOutputPattern() {
				return new DataPattern(new DataType[] { DataType.Double });
			}

			@Override
			public DataArray compute(DataArray inputValues) {
				DataArray da = new DataArray(getOutputPattern());
				Double a = (Double) inputValues.getValue(0);
				Double b = (Double) inputValues.getValue(1);
				da.setValue(0, a + b);
				return da;
			}
		};
		AtomicProcess add = new AtomicProcess("sum1", addUnit);
		System.out.println(add);
		if (!add.getValue(0).equals(DataType.Double.getNeuter()))
			throw new RuntimeException("Error");
		else
			System.out.println("ok");

		add.setDependencie(0, consts, 0);
		System.out.println(add);
		if (!add.getValue(0).equals(consts.getValue(0)))
			throw new RuntimeException("Error");
		else
			System.out.println("ok");

		add.setDependencie(1, consts, 1);
		System.out.println(add);
		add.setDependencie(1, null);
		System.out.println(add);
		add.setDependencie(1, consts, 1);
		System.out.println(add);
		System.out.println(add.getValues());
		if (!add.getValue(0).equals(new Double(15 + 20)))
			throw new RuntimeException("Error");
		else
			System.out.println("ok");
		try {
			add.setDependencie(0, add, 0);
			throw new RuntimeException("Error");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("ok");
		}
		System.out.println(add);
		try {
			add.setDependencie(2, add, 0);
			throw new RuntimeException("Error");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("ok");
		}

		AtomicProcess add2 = new AtomicProcess("sum2", addUnit);
		AtomicProcess add3 = new AtomicProcess("sum3", addUnit);
		add2.setDependencie(0, add);
		add2.setDependencie(1, add);
		add3.setDependencie(1, add2);
		System.out.println(add2);
		try {
			add.setDependencie(0, add2);
			throw new RuntimeException("Error");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("ok");
		}
		add2.setDependencie(0, null);
		add2.setDependencie(1, null);
		System.out.println(add2);
		add.setDependencie(0, add2);
		try {
			add2.setDependencie(0, add);
			throw new RuntimeException("Error");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("ok");
		}

		AtomicProcess consts2 = new AtomicProcess("consts", new Constant(DataType.Integer, new Integer(123)));
		System.out.println(consts2);
		System.out.println(consts2.getInputPattern());
		System.out.println(consts2.getOutputPattern());
		System.out.println(consts2.getValues());
		try {
			add2.setDependencie(0, consts2);
			throw new RuntimeException("Error");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("ok");
		}
		
		// test buffers
		InputBuffer buff1 = new InputBuffer(new DataPattern(new DataType[] { DataType.Double, DataType.Double }));
		AtomicProcess buffProcess1 = new AtomicProcess("buffProcess1", buff1);
		System.out.println(buffProcess1);
		System.out.println(buffProcess1.getValues());
		buff1.setValue(0, 15.2);
		System.out.println(buffProcess1);
		System.out.println(buffProcess1.getValues());

		OutputBuffer buff2 = new OutputBuffer(new DataPattern(new DataType[] { DataType.Double, DataType.Double }));
		AtomicProcess buffProcess2 = new AtomicProcess("buffProcess2", buff2);
		AtomicProcess consts3 = new AtomicProcess("consts3", new Constant(DataType.Double, 12.5));
		System.out.println(buff2.getValue(0));
		buffProcess2.setDependencie(0, consts3);
		System.out.println(buff2.getValue(0));
		buffProcess2.getValues();
		System.out.println(buff2.getValue(0));
		System.out.println(buffProcess2);
		System.out.println(buffProcess2.getValues());
		buffProcess2.setDependencie(1, consts3);

		// test complex processes
		ComplexProcess complex1 = new ComplexProcess("complex1",
				new DataPattern(new DataType[] { DataType.Double, DataType.Double }),
				new DataPattern(new DataType[] { DataType.Double, DataType.Double }));
		AtomicProcess add4 = new AtomicProcess("add4", new Add());
		add4.setDependencie(0, complex1.getInputProcess(), 0);
		add4.setDependencie(1, complex1.getInputProcess(), 1);
		AtomicProcess sub4 = new AtomicProcess("sub4", new Sub());
		add4.setDependencie(0, complex1.getInputProcess(), 0);
		add4.setDependencie(1, complex1.getInputProcess(), 1);
		sub4.setDependencie(0, complex1.getInputProcess(), 0);
		sub4.setDependencie(1, complex1.getInputProcess(), 1);
		complex1.getOutputProcess().setDependencie(0, add4, 0);
		complex1.getOutputProcess().setDependencie(1, sub4, 0);
		System.out.println(complex1);
		
		AtomicProcess complexProcess1 = new AtomicProcess("complexProcess1", complex1);
		complexProcess1.setDependencie(0, new AtomicProcess("a" ,new Constant(DataType.Double, 10.5)), 0);
		complexProcess1.setDependencie(1, new AtomicProcess("b" ,new Constant(DataType.Double, 9.5)), 0);
		System.out.println(complexProcess1);
		System.out.println(complexProcess1.getValues());
		
		AtomicProcess complexProcess2 = new AtomicProcess("complexProcess2", complex1);
		complexProcess2.setDependencie(0, new AtomicProcess("c" ,new Constant(DataType.Double, 5.0)), 0);
		complexProcess2.setDependencie(1, new AtomicProcess("d" ,new Constant(DataType.Double, 17.5)), 0);
		System.out.println(complexProcess2);
		System.out.println(complexProcess2.getValues());

	}
}
