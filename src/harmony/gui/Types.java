package harmony.gui;

import java.awt.Color;

public class Types {

	public static enum Command {
		DELETE
	}

	public static Color getDataColor(Class<?> type) {
		if (type == Integer.class)
			return Color.RED;
		else if (type == Float.class)
			return Color.GREEN;
		else if (type == Double.class)
			return Color.BLUE;
		else
			throw new IllegalArgumentException();
	}
}
