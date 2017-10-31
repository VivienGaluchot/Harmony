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

public class DataProcessTest {
	public static void main(String[] args) throws Exception {
		AtomicProcess consts = new AtomicProcess("consts", new ComputeUnit() {
			@Override
			public String getName() {
				return "c";
			}
			@Override
			public DataPattern getInputPattern() {
				return new DataPattern();
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
		if(!add.getValue(0).equals(DataType.Double.getNeuter()))
			throw new RuntimeException("Error");
		else
			System.out.println("ok");
		
		add.setDependencie(0, consts, 0);
		System.out.println(add);
		if(!add.getValue(0).equals(consts.getValue(0)))
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
		if(!add.getValue(0).equals(new Double(15 + 20)))
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
	}
}
