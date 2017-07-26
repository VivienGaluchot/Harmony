package harmony.gui;

import java.awt.Color;

public class Types {
	public static enum DataType {
		INTEGER, FLOAT, DOUBLE
	}

	public static enum Command {
		DELETE
	}

	public static Color getDataColor(DataType d) {
		if (d == DataType.INTEGER)
			return Color.RED;
		else if (d == DataType.FLOAT)
			return Color.GREEN;
		else
			return Color.BLUE;
	}
}
