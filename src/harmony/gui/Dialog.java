package harmony.gui;

import java.awt.Component;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

public class Dialog {

	public static void displayError(Component frame, String message) {
		JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static boolean YesNoDialog(Component frame, String question) {
		int res = JOptionPane.showConfirmDialog(frame, question);
		return res == JOptionPane.OK_OPTION;
	}

	public static Double DoubleDialog(Component frame, String msg, String defaultValue) {
		try {
			Double p = Double.parseDouble((String) JOptionPane.showInputDialog(frame, msg, "Informations",
					JOptionPane.PLAIN_MESSAGE, null, null, defaultValue));
			return p;
		} catch (NumberFormatException e) {
			displayError(frame, "Input value must be a real number");
			return null;
		}
	}

	public static String StringDialog(Component frame, String msg, String defaultValue) {
		return (String) JOptionPane.showInputDialog(frame, msg, "Informations", JOptionPane.PLAIN_MESSAGE, null, null,
				defaultValue);
	}

	public static Object JListDialog(Component frame, String text, List<Object> objects) {
		Object[] array = objects.toArray();
		return JListDialog(frame, text, array);
	}

	public static Object JListDialog(Component frame, String text, Set<Object> objects) {
		Object[] array = objects.toArray();
		return JListDialog(frame, text, array);
	}

	public static Object JListDialog(Component frame, String text, Object[] objects) {
		return JOptionPane.showInputDialog(null, text, "Selection", JOptionPane.QUESTION_MESSAGE, null, objects,
				objects[0]);
	}
}
