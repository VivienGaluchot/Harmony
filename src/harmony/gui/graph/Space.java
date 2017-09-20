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

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import harmony.data.DataDescriptor;
import harmony.data.DataGenerator;
import harmony.data.DataProcessor;
import harmony.data.Util;
import harmony.gui.Dialog;
import harmony.gui.DrawPanel;
import harmony.gui.Types;
import harmony.gui.graph.elements.GuiElement;
import harmony.gui.graph.elements.InPort;
import harmony.gui.graph.elements.Link;
import harmony.gui.graph.elements.Node;
import harmony.gui.graph.elements.OutPort;
import harmony.gui.graph.elements.Port;
import harmony.gui.graph.elements.nodes.SpaceInputNode;
import harmony.gui.graph.elements.nodes.SpaceOutputNode;
import harmony.gui.record.ChangeRecord;
import harmony.gui.record.RecordQueue;
import harmony.gui.record.Recordable;
import harmony.gui.record.StateRecord;
import harmony.math.Vector2D;

public class Space implements Recordable, MouseListener, MouseMotionListener, KeyListener {
	private DrawPanel panel;
	private String name;

	private ArrayList<Node> nodes;
	private SpaceInputNode inputNode;
	private SpaceOutputNode outputNode;
	private ArrayList<Link> links;

	private Link draggedLink = null;

	private boolean didDrag = false;
	private Vector2D initMousePos = null;
	private Vector2D initObjPos = null;

	private boolean alt_key_pressed = false;
	private boolean ctrl_key_pressed = false;

	private List<GuiElement> hovereds;
	private List<GuiElement> selecteds;
	private GuiElement clicked = null;

	private RecordQueue recordQueue;

	public Space(String name, List<DataGenerator> inputs, List<DataDescriptor> outputs) {
		this.name = name;

		nodes = new ArrayList<>();
		links = new ArrayList<>();

		hovereds = new ArrayList<>();
		selecteds = new ArrayList<>();

		recordQueue = new RecordQueue();

		inputNode = new SpaceInputNode(this, inputs);
		inputNode.pos = inputNode.pos.add(new Vector2D(-5, 0));
		recordQueue.addTrackedObject(inputNode);

		outputNode = new SpaceOutputNode(this, outputs);
		outputNode.pos = outputNode.pos.add(new Vector2D(5, 0));
		recordQueue.addTrackedObject(outputNode);

		recordQueue.addTrackedObject(this);
	}

	public String getName() {
		return name;
	}

	public void setDrawPanel(DrawPanel panel) {
		this.panel = panel;
		for (GuiElement el : getObjectList())
			el.setFather(panel);
	}

	public DrawPanel getDrawPanel() {
		return panel;
	}

	public SpaceInputNode getInputNode() {
		return inputNode;
	}

	public SpaceOutputNode getOutputNode() {
		return outputNode;
	}
	
	public String getHoveredElementInfo() {
		if(hovereds.size() > 0)
			return hovereds.get(0).toString();
		else
			return "";
	}

	// Objects

	public RecordQueue getRecordQueue() {
		return recordQueue;
	}

	public void addNode(Node n) {
		nodes.add(n);
		recordQueue.addTrackedObject(n);
	}

	public void removeNode(Node n) {
		if (n == inputNode)
			return;
		if (n == outputNode)
			return;
		Iterator<Link> iter = links.listIterator();
		while (iter.hasNext()) {
			Link l = iter.next();
			if (l.getOutPort().node == n || l.getInPort().node == n) {
				l.getInPort().setLink(null);
				iter.remove();
			}
		}
		nodes.remove(n);
		recordQueue.removeTrackedObject(n);
	}

	public void addLink(Link l) {
		// Check if link is making loops
		if (Util.isInDependenciesTree(l.getOutPort(), l.getInPort())) {
			JOptionPane.showMessageDialog(panel, "Computing loop detected, new link can't be added.", "Error",
					JOptionPane.ERROR_MESSAGE);
			// Do nothing
			return;
		}

		// Remove existing link with same end
		Link initEndlink = l.getInPort().getLink();
		if (initEndlink != null)
			removeLink(initEndlink);

		// Connect
		l.getInPort().setLink(l);
		// Add link
		links.add(l);
	}

	public void removeLink(Link l) {
		// Disconnect
		l.getInPort().setLink(null);
		// Remove
		links.remove(l);
	}

	private void sequenceNode(Node n, List<GuiElement> list) {
		list.add(n);
		for (InPort dp : n.getInPorts()) {
			list.add(dp);
			if (dp.getLink() != null)
				list.add(dp.getLink());
		}
		for (Port dp : n.getOutPorts())
			list.add(dp);
	}

	public List<GuiElement> getObjectList() {
		List<GuiElement> list = new ArrayList<>();
		if (inputNode != null)
			sequenceNode(inputNode, list);
		if (outputNode != null)
			sequenceNode(outputNode, list);
		for (Node n : nodes)
			sequenceNode(n, list);
		return list;
	}

	// Display

	public GuiElement getPointedObject(Vector2D p) {
		List<GuiElement> list = getObjectList();
		while (list.size() > 0) {
			GuiElement hcs = list.remove(list.size() - 1);
			if (hcs != null && hcs.contains(p))
				return hcs;
		}
		return null;
	}

	public synchronized void paint(Graphics g) {
		for (GuiElement hcs : getObjectList())
			hcs.paint(g);

		if (draggedLink != null)
			draggedLink.paint(g);
	}

	// GuiElement handle

	private void setHovered(GuiElement hovered) {
		for (GuiElement el : hovereds) {
			el.setHovered(false);
			// Update distant displays
			if (el.getFather() != this.getDrawPanel())
				el.getFather().repaint();
		}
		hovereds.clear();

		if (hovered != null) {
			hovereds.add(hovered);
			if (hovered instanceof DataProcessor) {
				Set<DataDescriptor> dependencies = Util.getDependencies((DataProcessor) hovered);
				for (DataDescriptor des : dependencies) {
					if (des instanceof GuiElement) {
						hovereds.add((GuiElement) des);
					}
				}
			}
		}
		for (GuiElement el : hovereds) {
			el.setHovered(true);
			// Update distant displays
			if (el.getFather() != this.getDrawPanel())
				el.getFather().repaint();
		}
	}

	private void setClicked(GuiElement clicked) {
		if (this.clicked != null) {
			this.clicked.setClicked(false);
		}
		this.clicked = clicked;
		if (this.clicked != null) {
			this.clicked.setClicked(true);
		}
	}

	private void setSelected(GuiElement selected) {
		for (GuiElement el : selecteds)
			el.setSelected(false);
		selecteds.clear();
		if (selected != null)
			addToSelecteds(selected);
	}

	private void addToSelecteds(GuiElement selected) {
		selecteds.add(selected);
		for (GuiElement el : selecteds)
			el.setSelected(true);
	}

	// MouseListener

	@Override
	public void mouseDragged(MouseEvent e) {
		Vector2D vecMouse = panel.transformMousePosition(e.getPoint());
		if (initMousePos != null) {
			if (clicked instanceof Node) {
				Node go = (Node) clicked;
				if (!go.isSelected())
					setSelected(go);
				if (initObjPos == null)
					initObjPos = go.pos.clone();
				Vector2D currentDrag = go.pos.clone();
				go.pos = initObjPos.add(vecMouse.subtract(initMousePos));
				if (alt_key_pressed) {
					go.pos.x = Math.round(4 * go.pos.x) / 4.;
					go.pos.y = Math.round(4 * go.pos.y) / 4.;
				}
				currentDrag = go.pos.subtract(currentDrag);
				for (GuiElement el : selecteds) {
					if (el instanceof Node && el != clicked) {
						((Node) el).pos = ((Node) el).pos.add(currentDrag);
					}
				}
			} else if (clicked instanceof InPort) {
				InPort inPort = (InPort) clicked;
				if (draggedLink == null)
					draggedLink = new Link(this, inPort.type, null, inPort);
				draggedLink.setClicked(true);
				draggedLink.setLoosePoint(vecMouse);

			} else if (clicked instanceof OutPort) {
				OutPort outPort = (OutPort) clicked;
				if (draggedLink == null)
					draggedLink = new Link(this, outPort.type, outPort, null);
				draggedLink.setClicked(true);
				draggedLink.setLoosePoint(vecMouse);
			}
		}
		setHovered(getPointedObject(vecMouse));
		didDrag = true;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Vector2D vecMouse = panel.transformMousePosition(e.getPoint());
		setHovered(getPointedObject(vecMouse));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Vector2D vecMouse = panel.transformMousePosition(e.getPoint());
		GuiElement el = getPointedObject(vecMouse);
		if (e.getButton() == MouseEvent.BUTTON1) {
			setClicked(el);
			initMousePos = vecMouse;
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			if (el instanceof Node) {
				Node go = (Node) el;
				go.showOpt(panel);
			}
		}
		didDrag = false;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Vector2D vecMouse = panel.transformMousePosition(e.getPoint());
		GuiElement el = getPointedObject(vecMouse);

		if (el != null && el.isClicked() && !didDrag)
			if (ctrl_key_pressed)
				addToSelecteds(el);
			else
				setSelected(el);
		else if (!didDrag)
			setSelected(null);
		setClicked(null);

		if (draggedLink != null) {
			if (el != null && el instanceof InPort) {
				InPort inPort = (InPort) el;
				if (draggedLink.getInPort() == null && inPort.type == draggedLink.type)
					draggedLink.setInPort(inPort);
				else
					Dialog.displayError(null, "Incompatible port types. The new link can't be added.");
			} else if (el != null && el instanceof OutPort) {
				OutPort outPort = (OutPort) el;
				if (draggedLink.getOutPort() == null && outPort.type == draggedLink.type)
					draggedLink.setOutPort(outPort);
				else
					Dialog.displayError(null, "Incompatible port types. The new link can't be added.");
			}
			if (draggedLink.getOutPort() != null && draggedLink.getInPort() != null) {
				draggedLink.setClicked(false);
				addLink(draggedLink);
				recordQueue.trackDiffs();
			}
			draggedLink = null;
		}

		if (didDrag) {
			recordQueue.trackDiffs();
		}

		initMousePos = null;
		initObjPos = null;
		mouseMoved(e);
	}

	// KeyListener

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_DELETE) {
			for (GuiElement el : selecteds)
				el.handleCommand(Types.Command.DELETE);

			recordQueue.trackDiffs();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ALT)
			alt_key_pressed = true;
		else if (e.getKeyCode() == KeyEvent.VK_CONTROL)
			ctrl_key_pressed = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ALT)
			alt_key_pressed = false;
		else if (e.getKeyCode() == KeyEvent.VK_CONTROL)
			ctrl_key_pressed = false;
	}

	// Record

	@Override
	public StateRecord getCurrentState() {
		return new SpaceStateRecord(this);
	}

	public class SpaceStateRecord extends StateRecord {
		private ArrayList<Node> recNodes;
		private ArrayList<Link> recLinks;

		public SpaceStateRecord(Space father) {
			super(father);
			recNodes = new ArrayList<>();
			recLinks = new ArrayList<>();
			for (Node n : father.nodes)
				recNodes.add(n);
			for (Link l : father.links)
				recLinks.add(l);
		}

		@Override
		public ChangeRecord getDiffs(StateRecord ref) {
			SpaceStateRecord stateRef = (SpaceStateRecord) ref;
			SpaceChangeRecord change = new SpaceChangeRecord((Space) getFather());
			for (Node n : recNodes)
				if (!stateRef.recNodes.contains(n))
					change.nodeRemoved(n);
			for (Node n : stateRef.recNodes)
				if (!recNodes.contains(n))
					change.nodeAdded(n);
			for (Link l : recLinks)
				if (!stateRef.recLinks.contains(l))
					change.linkRemoved(l);
			for (Link l : stateRef.recLinks)
				if (!recLinks.contains(l))
					change.linkAdded(l);

			if (change.isDiff())
				return change;
			else
				return null;
		}

	}

	public class SpaceChangeRecord extends ChangeRecord {
		private ArrayList<Node> nodesAdded;
		private ArrayList<Node> nodesRemoved;
		private ArrayList<Link> linksAdded;
		private ArrayList<Link> linksRemoved;

		public SpaceChangeRecord(Space father) {
			super(father);
			nodesAdded = new ArrayList<>();
			nodesRemoved = new ArrayList<>();
			linksAdded = new ArrayList<>();
			linksRemoved = new ArrayList<>();
		}

		public void nodeAdded(Node n) {
			nodesAdded.add(n);
		}

		public void nodeRemoved(Node n) {
			nodesRemoved.add(n);
		}

		public void linkAdded(Link l) {
			linksAdded.add(l);
		}

		public void linkRemoved(Link l) {
			linksRemoved.add(l);
		}

		public boolean isDiff() {
			return nodesAdded.size() > 0 || nodesRemoved.size() > 0 || linksAdded.size() > 0 || linksRemoved.size() > 0;
		}

		@Override
		public void undoChange() {
			for (Node n : nodesAdded)
				((Space) getFather()).removeNode(n);
			for (Node n : nodesRemoved)
				((Space) getFather()).addNode(n);
			for (Link l : linksAdded)
				((Space) getFather()).removeLink(l);
			for (Link l : linksRemoved)
				((Space) getFather()).addLink(l);
		}

		@Override
		public void redoChange() {
			for (Node n : nodesAdded)
				((Space) getFather()).addNode(n);
			for (Node n : nodesRemoved)
				((Space) getFather()).removeNode(n);
			for (Link l : linksAdded)
				((Space) getFather()).addLink(l);
			for (Link l : linksRemoved)
				((Space) getFather()).removeLink(l);
		}

	}

}
