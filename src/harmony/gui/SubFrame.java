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
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import harmony.data.basicSchemes.AddScheme;
import harmony.data.basicSchemes.DivideScheme;
import harmony.data.basicSchemes.MultiplyScheme;
import harmony.data.basicSchemes.SubstractScheme;
import harmony.gui.graph.elements.Node;
import harmony.gui.graph.elements.nodes.Constant;
import harmony.gui.graph.elements.nodes.Display;
import harmony.gui.graph.elements.nodes.NodeFactory;
import harmony.gui.graph.elements.nodes.ProcessNode;
import harmony.gui.graph.elements.nodes.SpaceNode;

public class SubFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private DrawPanel panel;

	public SubFrame(DrawPanel panel) {
		super();
		this.panel = panel;

		initMenu();

		setTitle("Harmony");
		setLayout(new GridBagLayout());
		getContentPane().setBackground(Color.white);

		add(panel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));

		setSize(new Dimension(800, 600));
		setVisible(true);
		setLocationRelativeTo(null);
	}

	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();

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

		setJMenuBar(menuBar);
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
		choices.add(new NodeWrapper(new Display(panel.getSpace())));
		choices.add(new NodeWrapper(new Constant(panel.getSpace())));
		choices.add(new NodeWrapper(new SpaceNode(panel.getSpace(), "SpaceNode")));
		NodeWrapper nw = (NodeWrapper) Dialog.JListDialog(this, "Node to add : ", choices);
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
}
