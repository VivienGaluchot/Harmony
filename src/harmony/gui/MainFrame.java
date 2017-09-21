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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import harmony.data.basicSchemes.AddScheme;
import harmony.data.basicSchemes.DivideScheme;
import harmony.data.basicSchemes.MultiplyScheme;
import harmony.data.basicSchemes.SinScheme;
import harmony.data.basicSchemes.SubstractScheme;
import harmony.gui.graph.Space;
import harmony.gui.graph.elements.Node;
import harmony.gui.graph.elements.nodes.Constant;
import harmony.gui.graph.elements.nodes.Display;
import harmony.gui.graph.elements.nodes.NodeFactory;
import harmony.gui.graph.elements.nodes.ProcessNode;
import harmony.gui.graph.elements.nodes.SpaceNode;
import harmony.gui.persist.Persistor;
import harmony.gui.graph.elements.nodes.FunctionCallNode;
import harmony.gui.graph.elements.nodes.FunctionNode;
import harmony.sound.License;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private DrawPanel panel;
	
	public MainFrame(Space space) {
		this(new DrawPanel(space));
	}

	public MainFrame(DrawPanel panel) {
		super();
		this.panel = panel;

		initMenu();

		setTitle("Harmony");
		setLayout(new GridBagLayout());
		getContentPane().setBackground(Color.white);

		add(panel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(800, 600));
		setVisible(true);
		setLocationRelativeTo(null);
	}

	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();

		// FILE
		JMenu file = new JMenu("File");
		file.setMnemonic('F');

		JMenuItem open = new JMenuItem("Open");
		open.setToolTipText("Open an existing project");
		open.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.Event.CTRL_MASK));
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				open();
			}
		});
		file.add(open);

		JMenuItem save = new JMenuItem("Save");
		save.setToolTipText("Save the current project");
		save.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK));
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				save();
			}
		});
		file.add(save);

		JMenuItem saveAs = new JMenuItem("Save as");
		saveAs.setToolTipText("Save the current project");
		saveAs.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S,
				java.awt.Event.SHIFT_MASK + java.awt.Event.CTRL_MASK));
		saveAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveAs();
			}
		});
		file.add(saveAs);

		menuBar.add(file);

		// EDIT
		JMenu edit = new JMenu("Edit");
		edit.setMnemonic('E');

		JMenuItem undo = new JMenuItem("Undo");
		undo.setToolTipText("Undo last action");
		undo.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.Event.CTRL_MASK));
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				undo();
			}
		});
		edit.add(undo);

		JMenuItem redo = new JMenuItem("Redo");
		redo.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.Event.CTRL_MASK));
		redo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				redo();
			}
		});
		edit.add(redo);

		edit.addSeparator();

		JMenuItem addNode = new JMenuItem("Add Node");
		addNode.setToolTipText("Add graph node to project");
		addNode.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.Event.CTRL_MASK));
		addNode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addNode();
			}
		});
		edit.add(addNode);

		menuBar.add(edit);

		// HELP
		JMenu help = new JMenu("Help");
		help.setMnemonic('H');

		JMenuItem license = new JMenuItem("License");
		license.setToolTipText("Software license");
		license.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showLicense();
			}
		});
		help.add(license);

		menuBar.add(help);

		setJMenuBar(menuBar);
	}

	// ACTIONS

	private void open() {
		Dialog.displayMessage(this, "Warning, open command experimental...");
		if (!Dialog.yesNoDialog(this, "Unsaved change will be lost, do you still want to continue ?"))
			return;
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Harmony project", "hrm");
		File inputFile = Dialog.fileDialog(this, filter);
		if(inputFile == null)
			return;
		FileInputStream streamIn = null;
		ObjectInputStream objectinputstream = null;
		try {
		    streamIn = new FileInputStream(inputFile);
		    objectinputstream = new ObjectInputStream(streamIn);
		    Persistor<Space> prs = (Persistor<Space>) objectinputstream.readObject();
		    if (prs != null) {
		    	panel.getSpace().clean();
		    	prs.update(panel.getSpace());
		    }
		} catch (Exception e) {
			Dialog.displayError(this, e.getMessage());
		    e.printStackTrace();
		} finally {
		    if(objectinputstream != null){
		        try {
					objectinputstream .close();
				} catch (IOException e) {
					Dialog.displayError(this, e.getMessage());
					e.printStackTrace();
				}
		    } 
		}
	}

	private void save() {
		Dialog.displayMessage(this, "Warning, save command experimental...");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Harmony project", "hrm");
		File outputFile = Dialog.fileDialog(this, filter);
		if(outputFile == null)
			return;
		Persistor<Space> p = panel.getSpace().getCurrentPersistRecord();
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		try {
			fout = new FileOutputStream(outputFile);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(p);
			oos.close();
		} catch (Exception e) {
			Dialog.displayError(this, e.getMessage());
			e.printStackTrace();
		} finally {
		    if(oos != null){
		        try {
					oos.close();
				} catch (Exception e) {
					Dialog.displayError(this, e.getMessage());
					e.printStackTrace();
				}
		    } 
		}
	}

	private void saveAs() {
		// TODO
		Dialog.displayMessage(this, "Save-as command not currently available...");
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

	private void addNode() {
		List<Object> choices = new ArrayList<>();
		choices.add(new NodeWrapper(NodeFactory.createTestNode(panel.getSpace())));
		choices.add(new NodeWrapper(new ProcessNode(panel.getSpace(), "Add", new AddScheme())));
		choices.add(new NodeWrapper(new ProcessNode(panel.getSpace(), "Substract", new SubstractScheme())));
		choices.add(new NodeWrapper(new ProcessNode(panel.getSpace(), "Divide", new DivideScheme())));
		choices.add(new NodeWrapper(new ProcessNode(panel.getSpace(), "Multiply", new MultiplyScheme())));
		choices.add(new NodeWrapper(new ProcessNode(panel.getSpace(), "Sin", new SinScheme())));
		choices.add(new NodeWrapper(new Display(panel.getSpace())));
		choices.add(new NodeWrapper(new Constant(panel.getSpace())));
		choices.add(new NodeWrapper(new SpaceNode(panel.getSpace(), "SpaceNode")));
		choices.add(new NodeWrapper(new FunctionNode(panel.getSpace(), "Function")));
		choices.add(new NodeWrapper(new FunctionCallNode(panel.getSpace())));
		NodeWrapper nw = (NodeWrapper) Dialog.listDialog(this, "Node to add : ", choices);
		if (nw != null) {
			panel.getSpace().addNode(nw.n);
			panel.getSpace().getRecordQueue().trackDiffs();
		}
	}

	private void undo() {
		panel.getSpace().getRecordQueue().undo();
		panel.repaint();
	}

	private void redo() {
		panel.getSpace().getRecordQueue().redo();
		panel.repaint();
	}

	private void showLicense() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame licenseFrame = new JFrame();
				licenseFrame.setLayout(new GridBagLayout());
				licenseFrame.setTitle("License");

				licenseFrame.add(new JLabel("Harmony, license informations"), new GridBagConstraints(0, 0, 1, 1, 0, 0,
						GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
				JTextPane pref = new JTextPane();
				pref.setEditable(false);
				pref.setText("Harmony : procedural sound waves generator\n" + "Copyright (C) 2017 - Vivien Galuchot\n"
						+ "\n"
						+ "This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, version 3 of the License.\n"
						+ "\n"
						+ "This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.");
				pref.setOpaque(false);
				licenseFrame.add(pref, new GridBagConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

				JTextPane textLisence = new JTextPane();
				textLisence.setEditable(false);
				textLisence.setText(License.license);
				textLisence.setFont(new Font("Consolas", Font.PLAIN, 12));
				JScrollPane scroll = new JScrollPane(textLisence);
				licenseFrame.add(scroll, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

				licenseFrame.setSize(new Dimension(600, 600));
				licenseFrame.setVisible(true);
				licenseFrame.setLocationRelativeTo(null);
			}
		});
	}
}
