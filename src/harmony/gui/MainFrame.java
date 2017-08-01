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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import harmony.gui.graph.elements.nodes.Default;
import harmony.sound.Licence;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private DrawPanel panel;

	public MainFrame(DrawPanel panel) {
		super();
		this.panel = panel;

		initMenu();

		setLayout(new GridBagLayout());
		getContentPane().setBackground(Color.white);

		add(new JLabel("-- Harmony --"), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
		add(panel, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
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

		JMenuItem licence = new JMenuItem("Licence");
		licence.setToolTipText("Software licence");
		licence.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showLicence();
			}
		});
		help.add(licence);

		menuBar.add(help);

		setJMenuBar(menuBar);
	}

	// ACTIONS

	private void open() {
		// TODO
		JOptionPane.showMessageDialog(this, "Open command not already avialable...");
	}

	private void save() {
		// TODO
		JOptionPane.showMessageDialog(this, "Save command not already avialable...");
	}

	private void saveAs() {
		// TODO
		JOptionPane.showMessageDialog(this, "Save-as command not already avialable...");
	}

	private void addNode() {
		panel.getSpace().addNode(new Default(panel.getSpace()));
	}

	private void undo() {
		panel.getSpace().getRecordQueue().undo();
		panel.repaint();
	}

	private void redo() {
		panel.getSpace().getRecordQueue().redo();
		panel.repaint();
	}

	private void showLicence() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame licenceFrame = new JFrame();
				licenceFrame.setLayout(new GridBagLayout());

				licenceFrame.add(new JLabel("Harmony Software Copyright (C) 2017 Vivien Galuchot"),
						new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
								new Insets(10, 10, 10, 10), 0, 0));
				JTextPane text = new JTextPane();
				text.setEditable(false);
				text.setText(Licence.licence);
				text.setFont(new Font("Consolas", Font.PLAIN, 12));
				JScrollPane scroll = new JScrollPane(text);
				licenceFrame.add(scroll, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

				licenceFrame.setSize(new Dimension(550, 600));
				licenceFrame.setVisible(true);
				licenceFrame.setLocationRelativeTo(null);
			}
		});
	}
}
