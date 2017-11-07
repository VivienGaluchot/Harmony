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

package harmony.processcore;

import harmony.processcore.data.DataArray;
import harmony.processcore.data.DataPattern;
import harmony.processcore.data.DataType;
import harmony.processcore.data.DataTypes;
import harmony.processcore.process.ComputeUnit;
import harmony.processcore.process.ProceduralUnit;
import harmony.processcore.process.Process;
import harmony.processcore.process.units.maths.Add;
import harmony.processcore.process.units.maths.Constant;
import harmony.processcore.process.units.maths.Sub;
import harmony.processcore.process.units.utils.InputBuffer;
import harmony.processcore.process.units.utils.OutputBuffer;

public class DataProcessTest {
	public static void main(String[] args) throws Exception {
		Process consts = new Process("consts", new ComputeUnit() {
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
				return new DataPattern(new DataType[] { DataTypes.Double, DataTypes.Double });
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
				return new DataPattern(new DataType[] { DataTypes.Double, DataTypes.Double });
			}

			@Override
			public DataPattern getOutputPattern() {
				return new DataPattern(new DataType[] { DataTypes.Double });
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
		Process add = new Process("sum1", addUnit);
		System.out.println(add);
		if (!add.getValue(0).equals(DataTypes.Double.getNeuter()))
			throw new RuntimeException("Error");
		else
			System.out.println("ok");

		add.setDependencie(0, consts.getOutput(0));
		System.out.println(add);
		if (!add.getValue(0).equals(consts.getValue(0)))
			throw new RuntimeException("Error");
		else
			System.out.println("ok");

		add.setDependencie(1, consts.getOutput(1));
		System.out.println(add);
		add.resetDependencie(1);
		System.out.println(add);
		add.setDependencie(1, consts.getOutput(1));
		System.out.println(add);
		System.out.println(add.getValues());
		if (!add.getValue(0).equals(new Double(15 + 20)))
			throw new RuntimeException("Error");
		else
			System.out.println("ok");
		try {
			add.setDependencie(0, add.getOutput(0));
			throw new RuntimeException("Error");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("ok");
		}
		System.out.println(add);
		try {
			add.setDependencie(2, add.getOutput(0));
			throw new RuntimeException("Error");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("ok");
		}

		Process add2 = new Process("sum2", addUnit);
		Process add3 = new Process("sum3", addUnit);
		add2.setDependencie(0, add.getOutput(0));
		add2.setDependencie(1, add.getOutput(0));
		add3.setDependencie(1, add2.getOutput(0));
		System.out.println(add2);
		try {
			add.setDependencie(0, add2.getOutput(0));
			throw new RuntimeException("Error");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("ok");
		}
		add2.setDependencie(0, null);
		add2.setDependencie(1, null);
		System.out.println(add2);
		add.setDependencie(0, add2.getOutput(0));
		try {
			add2.setDependencie(0, add.getOutput(0));
			throw new RuntimeException("Error");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("ok");
		}

		Process consts2 = new Process("consts", new Constant(DataTypes.Integer, new Integer(123)));
		System.out.println(consts2);
		System.out.println(consts2.getInputPattern());
		System.out.println(consts2.getOutputPattern());
		System.out.println(consts2.getValues());
		try {
			add2.setDependencie(0, consts2.getOutput(0));
			throw new RuntimeException("Error");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("ok");
		}

		// test buffers
		InputBuffer buff1 = new InputBuffer(new DataPattern(new DataType[] { DataTypes.Double, DataTypes.Double }));
		Process buffProcess1 = new Process("buffProcess1", buff1);
		System.out.println(buffProcess1);
		System.out.println(buffProcess1.getValues());
		buff1.setValue(0, 15.2);
		System.out.println(buffProcess1);
		System.out.println(buffProcess1.getValues());

		OutputBuffer buff2 = new OutputBuffer(new DataPattern(new DataType[] { DataTypes.Double, DataTypes.Double }));
		Process buffProcess2 = new Process("buffProcess2", buff2);
		Process consts3 = new Process("consts3", new Constant(DataTypes.Double, 12.5));
		System.out.println(buff2.getValue(0));
		buffProcess2.setDependencie(0, consts3.getOutput(0));
		System.out.println(buff2.getValue(0));
		buffProcess2.getValues();
		System.out.println(buff2.getValue(0));
		System.out.println(buffProcess2);
		System.out.println(buffProcess2.getValues());
		buffProcess2.setDependencie(1, consts3.getOutput(0));

		// test complex processes
		ProceduralUnit complex1 = new ProceduralUnit("complex1",
				new DataPattern(new DataType[] { DataTypes.Double, DataTypes.Double }),
				new DataPattern(new DataType[] { DataTypes.Double, DataTypes.Double }));
		Process add4 = new Process("add4", new Add());
		add4.setDependencie(0, complex1.getInputProcess().getOutput(0));
		add4.setDependencie(1, complex1.getInputProcess().getOutput(1));
		Process sub4 = new Process("sub4", new Sub());
		add4.setDependencie(0, complex1.getInputProcess().getOutput(0));
		add4.setDependencie(1, complex1.getInputProcess().getOutput(1));
		sub4.setDependencie(0, complex1.getInputProcess().getOutput(0));
		sub4.setDependencie(1, complex1.getInputProcess().getOutput(1));
		complex1.getOutputProcess().setDependencie(0, add4.getOutput(0));
		complex1.getOutputProcess().setDependencie(1, sub4.getOutput(0));
		System.out.println(complex1);

		Process complexProcess1 = new Process("complexProcess1", complex1);
		complexProcess1.setDependencie(0, new Process("a", new Constant(DataTypes.Double, 10.5)).getOutput(0));
		complexProcess1.setDependencie(1, new Process("b", new Constant(DataTypes.Double, 9.5)).getOutput(0));
		System.out.println(complexProcess1);
		System.out.println(complexProcess1.getValues());

		Process complexProcess2 = new Process("complexProcess2", complex1);
		complexProcess2.setDependencie(0, new Process("c", new Constant(DataTypes.Double, 5.0)).getOutput(0));
		complexProcess2.setDependencie(1, new Process("d", new Constant(DataTypes.Double, 17.5)).getOutput(0));
		System.out.println(complexProcess2);
		System.out.println(complexProcess2.getValues());

	}
}
