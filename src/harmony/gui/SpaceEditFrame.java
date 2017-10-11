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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import harmony.Ressources;
import harmony.gui.graph.Space;
import harmony.gui.graph.SpaceController;

public class SpaceEditFrame extends JFrame implements ComponentListener {
	private static final long serialVersionUID = 1L;

	protected AnimationRunner runner;
	protected SpaceController controller;

	protected JMenuBar menuBar;

	protected JMenu file;
	protected JMenuItem open;
	protected JMenuItem save;
	protected JMenuItem saveAs;

	protected JMenu edit;
	protected JMenuItem undo;
	protected JMenuItem redo;
	protected JMenuItem addNode;

	public SpaceEditFrame(Space space) {
		super();
		controller = new SpaceController(space);
		runner = new AnimationRunner(space);
		
		updateTitle();
		initMenu();
		addComponentListener(this);
		
		List<Image> icons = new ArrayList<Image>();
		icons.add(Ressources.icon16.getImage());
		icons.add(Ressources.icon32.getImage());
		icons.add(Ressources.icon64.getImage());
		icons.add(Ressources.icon128.getImage());
		icons.add(Ressources.icon256.getImage());
		setIconImages(icons);
		
		setLayout(new GridBagLayout());
		getContentPane().setBackground(Color.white);

		add(space, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));

		setSize(new Dimension(800, 600));
		setVisible(true);
		setLocationRelativeTo(null);
		
		runner.start();
	}

	protected void initMenu() {
		menuBar = new JMenuBar();

		// FILE
		file = new JMenu("File");
		file.setMnemonic('F');
		
		open = new JMenuItem("Open");
		open.setToolTipText("Open an existing project");
		open.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.Event.CTRL_MASK));
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.open();
				updateTitle();
			}
		});
		file.add(open);

		save = new JMenuItem("Save");
		save.setToolTipText("Save the current project");
		save.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK));
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.save();
				updateTitle();
			}
		});
		file.add(save);

		saveAs = new JMenuItem("Save as");
		saveAs.setToolTipText("Save the current project");
		saveAs.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S,
				java.awt.Event.SHIFT_MASK + java.awt.Event.CTRL_MASK));
		saveAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.saveAs();
				updateTitle();
			}
		});
		file.add(saveAs);

		menuBar.add(file);

		// EDIT
		edit = new JMenu("Edit");
		edit.setMnemonic('E');

		undo = new JMenuItem("Undo");
		undo.setToolTipText("Undo last action");
		undo.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.Event.CTRL_MASK));
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.undo();
			}
		});
		edit.add(undo);

		redo = new JMenuItem("Redo");
		redo.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.Event.CTRL_MASK));
		redo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.redo();
			}
		});
		edit.add(redo);

		edit.addSeparator();

		addNode = new JMenuItem("Add Node");
		addNode.setToolTipText("Add graph node to project");
		addNode.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.Event.CTRL_MASK));
		addNode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.addNode();
			}
		});
		edit.add(addNode);

		menuBar.add(edit);

		setJMenuBar(menuBar);
	}
	
	private void updateTitle() {
		String mainTitle = "Harmony";
		if (controller != null) {
			File currentFile = controller.getCurrentFile();
			if (currentFile != null) {
				setTitle(mainTitle + " - " + currentFile.toString());
				return;
			}
		}
		
		setTitle(mainTitle);
	}
	
	
	// ComponentListener

	@Override
	public void componentHidden(ComponentEvent arg0) {
		updateTitle();
		runner.pauseAnimation();
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		updateTitle();
		runner.resumeAnimation();
	}
}
