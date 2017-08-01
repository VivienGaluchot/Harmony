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

import javax.swing.JOptionPane;

import harmony.gui.DrawPanel;
import harmony.gui.Types;
import harmony.gui.graph.elements.GuiElement;
import harmony.gui.graph.elements.InPort;
import harmony.gui.graph.elements.Link;
import harmony.gui.graph.elements.Node;
import harmony.gui.graph.elements.OutPort;
import harmony.gui.graph.elements.Port;
import harmony.gui.graph.elements.nodes.Default;
import harmony.gui.graph.elements.nodes.Display;
import harmony.gui.record.ChangeRecord;
import harmony.gui.record.RecordQueue;
import harmony.gui.record.Recordable;
import harmony.gui.record.StateRecord;
import harmony.math.Vector2D;

public class Space implements Recordable, MouseListener, MouseMotionListener, KeyListener {
	private DrawPanel panel;

	private ArrayList<Node> nodes;
	private ArrayList<Link> links;

	private Link draggedLink = null;

	private boolean didDrag = false;
	private Vector2D initMousePos = null;

	private GuiElement hovered = null;
	private GuiElement clicked = null;
	private GuiElement selected = null;

	private RecordQueue recordQueue;

	public Space(DrawPanel panel) {
		this.panel = panel;

		nodes = new ArrayList<>();
		links = new ArrayList<>();

		recordQueue = new RecordQueue();

		Node g1 = new Default(this);
		g1.pos = g1.pos.add(new Vector2D(-2, 0));
		addNode(g1);

		Node g2 = new Default(this);
		g2.pos = g2.pos.add(new Vector2D(2, 0));
		addNode(g2);

		Node g3 = new Display(this);
		g3.pos = g3.pos.add(new Vector2D(0, 3));
		addNode(g3);

		recordQueue.addTrackedObject(this);
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
		for (Iterator<Link> iter = links.listIterator(); iter.hasNext();) {
			Link l = iter.next();
			if (l.getStart().father == n || l.getEnd().father == n) {
				iter.remove();
			}
		}
		nodes.remove(n);
		recordQueue.removeTrackedObject(n);
	}

	public void addLink(Link l) {
		Link initEndlink = l.getEnd().getLink();

		// Connect
		l.getEnd().setLink(l);
		
		// Check if link is making loops
		if (l.getStart().containsComputingLoops()) {
			JOptionPane.showMessageDialog(panel, "Computing loop detected, new link can't be added.", "Error",
					JOptionPane.ERROR_MESSAGE);
			// Reset end link as it was
			l.getEnd().setLink(initEndlink);
			return;
		}

		// Remove existing link with same end
		if (initEndlink != null && initEndlink != l)
			removeLink(initEndlink);

		// Add link
		links.add(l);
	}

	public void removeLink(Link l) {
		l.getEnd().setLink(null);
		links.remove(l);
	}

	public List<GuiElement> getObjectList() {
		List<GuiElement> list = new ArrayList<>();
		for (Node n : nodes) {
			list.add(n);
			for (Port dp : n.getInPorts())
				list.add(dp);
			for (Port dp : n.getOutPorts())
				list.add(dp);
		}
		for (Link l : links) {
			list.add(l);
		}
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
		if (this.hovered != null) {
			this.hovered.setHovered(false);
		}
		this.hovered = hovered;
		if (this.hovered != null) {
			this.hovered.setHovered(true);
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
		if (this.selected != null) {
			this.selected.setSelected(false);
		}
		this.selected = selected;
		if (this.selected != null) {
			this.selected.setSelected(true);
		}
	}

	// MouseListener

	@Override
	public void mouseDragged(MouseEvent e) {
		Vector2D vecMouse = panel.transformMousePosition(e.getPoint());
		if (initMousePos != null) {
			if (clicked instanceof Node) {
				Node go = (Node) clicked;
				go.pos = go.pos.add(vecMouse.subtract(initMousePos));
				initMousePos = vecMouse;
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
		setSelected(null);
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
			initMousePos = panel.transformMousePosition(e.getPoint());
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
			setSelected(el);
		else
			setSelected(null);
		setClicked(null);

		if (draggedLink != null) {
			if (el != null && el instanceof InPort) {
				InPort inPort = (InPort) el;
				if (draggedLink.getEnd() == null && inPort.type == draggedLink.type)
					draggedLink.setEnd(inPort);
				if (draggedLink.getStart() != null && draggedLink.getEnd() != null) {
					draggedLink.setClicked(false);
					addLink(draggedLink);
				}
			} else if (el != null && el instanceof OutPort) {
				OutPort outPort = (OutPort) el;
				if (draggedLink.getStart() == null && outPort.type == draggedLink.type)
					draggedLink.setStart(outPort);
				if (draggedLink.getStart() != null && draggedLink.getEnd() != null) {
					draggedLink.setClicked(false);
					addLink(draggedLink);
				}
			}
			draggedLink = null;
			initMousePos = null;
		}
		mouseMoved(e);

		recordQueue.trackDiffs();
	}

	// KeyListener

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_DELETE)
			if (selected != null)
				selected.handleCommand(Types.Command.DELETE);

		recordQueue.trackDiffs();
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

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
