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

package harmony.processcore.data;

import harmony.math.Vector2D;

public class DataTypes {
	// Base types
	public final static DataType Double = new SimpleDataType(Double.class, new Double(0.0));
	public final static DataType Integer = new SimpleDataType(Integer.class, new Integer(0));
	public final static DataType Boolean = new SimpleDataType(Boolean.class, new Boolean(false));
	
	// Math
	public final static DataType Vector2D = new SimpleDataType(Vector2D.class, new Vector2D());
	
	// List
	public final static DataType[] dataTypes = {Double, Integer};
}