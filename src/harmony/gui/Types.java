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

package harmony.gui;

import java.awt.Color;
import java.text.DecimalFormat;

import harmony.processcore.data.DataType;
import harmony.processcore.data.DataTypes;

public class Types {

	private static final Color DoubleColor = new Color(90, 170, 255);

	public static enum Command {
		DELETE
	}

	public static Color getDataColor(DataType type) {
		if (type.includes(DataTypes.Integer))
			return Color.RED;
		else if (type.includes(DataTypes.Double))
			return DoubleColor;
		else if (type.includes(DataTypes.Boolean))
			return Color.MAGENTA;
		else
			throw new IllegalArgumentException("Wrong data type : " + type);
	}

	public static String getDataString(Object data) {
		DecimalFormat df = new DecimalFormat("#");
		df.setMinimumIntegerDigits(1);
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		if (data instanceof Integer)
			return df.format(data);
		else if (data instanceof Float)
			return df.format(data);
		else if (data instanceof Double)
			return df.format(data);
		else if (data == null)
			return "_";
		else
			return data.toString();
	}
}
