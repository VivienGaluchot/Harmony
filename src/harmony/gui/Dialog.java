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

import java.awt.Component;
import java.io.File;
import java.util.List;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Dialog {
	public static void displayMessage(Component frame, String message) {
		JOptionPane.showMessageDialog(frame, message);
	}

	public static void displayError(Component frame, String message) {
		JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static boolean yesNoDialog(Component frame, String question) {
		int res = JOptionPane.showConfirmDialog(frame, question);
		return res == JOptionPane.OK_OPTION;
	}

	public static Double doubleDialog(Component frame, String msg, String defaultValue) {
		try {
			String pStr = (String) JOptionPane.showInputDialog(frame, msg, "Informations", JOptionPane.PLAIN_MESSAGE,
					null, null, defaultValue);
			if(pStr != null) {
				Double p = Double.parseDouble(pStr);
				return p;
			}
		} catch (NumberFormatException e) {
			displayError(frame, "Input value must be a real number");
			return null;
		}
		return null;
	}

	public static String stringDialog(Component frame, String msg, String defaultValue) {
		return (String) JOptionPane.showInputDialog(frame, msg, "Informations", JOptionPane.PLAIN_MESSAGE, null, null,
				defaultValue);
	}

	public static Object listDialog(Component frame, String text, List<Object> objects) {
		Object[] array = objects.toArray();
		return listDialog(frame, text, array);
	}

	public static Object listDialog(Component frame, String text, Set<Object> objects) {
		Object[] array = objects.toArray();
		return listDialog(frame, text, array);
	}

	public static Object listDialog(Component frame, String text, Object[] objects) {
		return JOptionPane.showInputDialog(null, text, "Selection", JOptionPane.QUESTION_MESSAGE, null, objects,
				objects[0]);
	}

	public static File fileDialog(Component frame, FileNameExtensionFilter filter, String approveButtonText,
			File currentDirectory) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setApproveButtonText(approveButtonText);
		fileChooser.setFileFilter(filter);
		fileChooser.setCurrentDirectory(currentDirectory);
		int res = fileChooser.showOpenDialog(frame);
		if (res == JFileChooser.APPROVE_OPTION)
			return fileChooser.getSelectedFile();
		return null;
	}
}
