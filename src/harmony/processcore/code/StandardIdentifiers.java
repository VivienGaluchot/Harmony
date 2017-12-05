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

package harmony.processcore.code;

import harmony.processcore.process.units.ComputeUnit;
import harmony.processcore.process.units.maths.Add;
import harmony.processcore.process.units.maths.Multiply;
import harmony.processcore.process.units.maths.Sub;

public class StandardIdentifiers {

	public final static ComputeUnit[] computeUnits = {new Add(), new Sub(), new Multiply()};
	
	public final static ComputeUnit getStandardComputeUnit(String name) {
		for (ComputeUnit computeUnit : computeUnits) {
			if (computeUnit.getName() == name) {
				return computeUnit;
			}
		}
		throw new IllegalArgumentException("Can't find standard compute unit with name \'" + name + "\'");
	}
	
}
