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

import harmony.gui.graph.elements.nodes.FunctionNode;

public class Types {

	private static final Color DoubleColor = new Color(90, 170, 255);

	public static enum Command {
		DELETE
	}

	public static Color getDataColor(Class<?> type) {
		if (type == Integer.class)
			return Color.RED;
		else if (type == Float.class)
			return Color.GREEN;
		else if (type == Double.class)
			return DoubleColor;
		else if (type == FunctionNode.class)
			return Color.MAGENTA;
		else
			throw new IllegalArgumentException(type.getName());
	}
}
