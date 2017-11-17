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

package harmony.gui.graph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileNameExtensionFilter;

import com.oracle.jrockit.jfr.DataType;

import harmony.gui.Dialog;
import harmony.gui.graph.elements.Node;
import harmony.gui.persist.Persistor;
import harmony.processcore.data.DataTypes;
import harmony.processcore.process.HrmProcess;
import harmony.processcore.process.units.maths.Add;
import harmony.processcore.process.units.maths.Constant;
import harmony.processcore.process.units.maths.Sub;

public class SpaceController {
	Space space = null;
	File currentFile = null;

	public SpaceController(Space space) {
		this.space = space;
	}

	public void open() {
		Dialog.displayMessage(space, "Warning, open command experimental...");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Harmony project", "hrm");
		File inputFile = Dialog.fileDialog(space, filter, "Open", currentFile);
		if (inputFile == null)
			return;
		try {
			@SuppressWarnings("unchecked")
			Persistor<Space> prs = (Persistor<Space>) Persistor.load(inputFile);
			if (prs != null) {
				if (space.isEmpty()
						&& !Dialog.yesNoDialog(space, "Unsaved change will be lost, do you still want to continue ?"))
					return;
				space.clean();
				prs.update(space);
				currentFile = inputFile;
			}
		} catch (ClassNotFoundException e) {
			Dialog.displayError(space, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Dialog.displayError(space, e.getMessage());
			e.printStackTrace();
		}
	}

	public File getCurrentFile() {
		return currentFile;
	}

	public void save() {
		if (currentFile == null) {
			saveAs();
			return;
		}
		Dialog.displayMessage(space, "Warning, save command experimental...");
		persistSpace(currentFile);
	}

	public void saveAs() {
		Dialog.displayMessage(space, "Warning, save-as command experimental...");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Harmony project", "hrm");
		File outputFile = Dialog.fileDialog(space, filter, "Save as", currentFile);
		if (outputFile == null)
			return;
		if (getExtension(outputFile.getName()).compareTo("hrm") != 0)
			outputFile = new File(outputFile.getPath() + ".hrm");
		if (outputFile.exists()
				&& !Dialog.yesNoDialog(space, "Warning, file already existing. Do you want to overwrite it ?"))
			return;
		persistSpace(outputFile);
	}

	public void undo() {
		space.getRecordQueue().undo();
		space.repaint();
	}

	public void redo() {
		space.getRecordQueue().redo();
		space.repaint();
	}

	public void addNode() {
		List<Object> choices = new ArrayList<>();
		choices.add(new NodeWrapper(new Node(space, new HrmProcess("add", new Add()))));
		choices.add(new NodeWrapper(new Node(space, new HrmProcess("sub", new Sub()))));
		choices.add(new NodeWrapper(new Node(space, new HrmProcess("constant", new Constant(DataTypes.Double, 0.0)))));
		NodeWrapper nw = (NodeWrapper) Dialog.listDialog(space, "Node to add : ", choices);
		if (nw != null) {
			space.addNode(nw.n);
			space.getRecordQueue().trackDiffs();
		}
	}

	/**
	 * Allow to display node's name in list
	 */
	private class NodeWrapper {
		public Node n;

		public NodeWrapper(Node n) {
			this.n = n;
		}

		@Override
		public String toString() {
			return n.getName();
		}
	}

	// Utils

	private String getExtension(String fileName) {
		String extension = "";
		int i = fileName.lastIndexOf('.');
		int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
		if (i > p) {
			extension = fileName.substring(i + 1);
		}
		System.out.println(fileName + " " + extension);
		return extension;
	}

	private void persistSpace(File path) {
		Persistor<Space> p = space.getCurrentPersistRecord();
		try {
			p.persist(path);
		} catch (IOException e) {
			Dialog.displayError(space, e.getMessage());
			e.printStackTrace();
		}
		currentFile = path;
	}
}
