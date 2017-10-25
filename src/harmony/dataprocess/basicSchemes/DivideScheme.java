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

package harmony.dataprocess.basicSchemes;

import java.util.Map;

import harmony.dataprocess.model.DataDescriptor;
import harmony.dataprocess.model.DataGenerator;

public class DivideScheme extends ABScheme {

	@Override
	public String getDataName() {
		return a.getDataName() + " / " + b.getDataName();
	}

	@Override
	public Object process(Map<DataDescriptor, DataGenerator> generatorMap) {
		Double bValue = findValue(b, generatorMap);
		if (bValue != 0)
			return findValue(a, generatorMap) / bValue;
		else
			return null;
	}

}
