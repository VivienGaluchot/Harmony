package sound.gui;

import java.awt.Color;

public class Types {
	public static enum Data {
		INTEGER, FLOAT, DOUBLE
	}
	
	public static enum IO {
		IN, OUT
	}
	
	public static enum Command {
		DELETE
	}
	
	public static Color getDataColor(Data d){
		if (d == Data.INTEGER)
			return Color.RED;
		else if (d == Data.FLOAT)
			return Color.GREEN;
		else
			return Color.BLUE;
	}
}
