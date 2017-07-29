package harmony.gui.graph;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JDialog;
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
import harmony.math.Vector2D;

public class Space implements Recordable, MouseListener, MouseMotionListener, KeyListener {
	private DrawPanel panel;

	private ArrayList<Node> lastRecordedNodes;
	private ArrayList<Node> nodes;
	private ArrayList<Link> lastRecordedLinks;
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

		lastRecordedNodes = new ArrayList<>();
		nodes = new ArrayList<>();
		lastRecordedLinks = new ArrayList<>();
		links = new ArrayList<>();

		recordQueue = new RecordQueue();

		Set<ChangeRecord> set = new HashSet<>();

		Node g1 = new Default(this);
		g1.pos = g1.pos.add(new Vector2D(-2, 0));
		set.add(g1.getCurrentRecord());
		addNodeOffRecord(g1);

		Node g2 = new Default(this);
		g2.pos = g2.pos.add(new Vector2D(2, 0));
		set.add(g2.getCurrentRecord());
		addNodeOffRecord(g2);

		Node g3 = new Display(this);
		g3.pos = g3.pos.add(new Vector2D(0, 3));
		set.add(g3.getCurrentRecord());
		addNodeOffRecord(g3);

		recordQueue.addRecords(set);
		updateObjectRecord();
	}

	// Objects

	public void updateObjectRecord() {
		Set<ChangeRecord> set = new HashSet<>();
		set.add(getCurrentRecord());
		recordQueue.addRecords(set);
	}

	public void addNode(Node n) {
		addNodeOffRecord(n);
		updateObjectRecord();
	}

	public void addNodeOffRecord(Node n) {
		nodes.add(n);
	}

	public void removeNode(Node n) {
		removeNodeOffRecord(n);
		updateObjectRecord();
	}

	public void removeNodeOffRecord(Node n) {
		for (Iterator<Link> iter = links.listIterator(); iter.hasNext();) {
			Link l = iter.next();
			if (l.getStart().father == n || l.getEnd().father == n) {
				iter.remove();
			}
		}
		nodes.remove(n);
	}

	public void addLink(Link l) {
		addLinkOffRecord(l);
		updateObjectRecord();
	}

	public void addLinkOffRecord(Link l) {
		// Remove existing links with same end
		for (Iterator<Link> iter = links.listIterator(); iter.hasNext();) {
			Link ol = iter.next();
			if (ol.getEnd() == l.getEnd()) {
				iter.remove();
			}
		}

		// Add
		links.add(l);

		// Check if link is making loops
		if (l.getStart().containsComputingLoops()) {
			JOptionPane.showMessageDialog(panel, "Computing loop detected, the new link will be removed.", "Error",
					JOptionPane.ERROR_MESSAGE);
			removeLinkOffRecord(l);
		}
	}

	public void removeLink(Link l) {
		removeLinkOffRecord(l);
		updateObjectRecord();
	}

	public void removeLinkOffRecord(Link l) {
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

	// RecordQueue

	public void undo() {
		Set<ChangeRecord> records = recordQueue.getUndoRecords();
		if (records != null) {
			for (ChangeRecord record : records) {
				record.undoChange();
			}
		}
	}

	public void redo() {
		Set<ChangeRecord> records = recordQueue.getRedoRecords();
		if (records != null) {
			for (ChangeRecord record : records) {
				record.redoChange();
			}
		}
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
		if (clicked instanceof Node && didDrag) {
			Set<ChangeRecord> set = new HashSet<>();
			set.add(((Node) clicked).getCurrentRecord());
			recordQueue.addRecords(set);
		}

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
	}

	// KeyListener

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_DELETE)
			if (selected != null)
				selected.handleCommand(Types.Command.DELETE);
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	// Record

	@Override
	public ChangeRecord getCurrentRecord() {
		SpaceRecord sr = new SpaceRecord(this);

		// added node
		for (Node n : nodes)
			if (!lastRecordedNodes.contains(n))
				sr.nodesAdded.add(n);

		// removed node
		for (Node n : lastRecordedNodes)
			if (!nodes.contains(n))
				sr.nodesRemoved.add(n);

		// added link
		for (Link l : links)
			if (!lastRecordedLinks.contains(l))
				sr.linksAdded.add(l);

		// removed link
		for (Link l : lastRecordedLinks)
			if (!nodes.contains(l))
				sr.linksRemoved.add(l);

		lastRecordedNodes.clear();
		for (Node n : nodes)
			lastRecordedNodes.add(n);
		lastRecordedLinks.clear();
		for (Link l : links)
			lastRecordedLinks.add(l);

		return sr;
	}

	public class SpaceRecord extends ChangeRecord {

		private Space father;

		public ArrayList<Node> nodesAdded;
		public ArrayList<Node> nodesRemoved;
		public ArrayList<Link> linksAdded;
		public ArrayList<Link> linksRemoved;

		public SpaceRecord(Space father) {
			super(father);
			this.father = father;
			nodesAdded = new ArrayList<>();
			nodesRemoved = new ArrayList<>();
			linksAdded = new ArrayList<>();
			linksRemoved = new ArrayList<>();
		}

		@Override
		public void undoChange() {
			for (Node n : nodesAdded)
				father.removeNodeOffRecord(n);
			for (Node n : nodesRemoved)
				father.addNodeOffRecord(n);
			for (Link l : linksAdded)
				father.removeLinkOffRecord(l);
			for (Link l : linksRemoved)
				father.addLinkOffRecord(l);

			father.lastRecordedNodes.clear();
			for (Node n : father.nodes)
				father.lastRecordedNodes.add(n);
			father.lastRecordedLinks.clear();
			for (Link l : father.links)
				father.lastRecordedLinks.add(l);
		}

		@Override
		public void redoChange() {
			for (Node n : nodesAdded)
				father.addNodeOffRecord(n);
			for (Node n : nodesRemoved)
				father.removeNodeOffRecord(n);
			for (Link l : linksAdded)
				father.addLinkOffRecord(l);
			for (Link l : linksRemoved)
				father.removeLinkOffRecord(l);

			father.lastRecordedNodes.clear();
			for (Node n : father.nodes)
				father.lastRecordedNodes.add(n);
			father.lastRecordedLinks.clear();
			for (Link l : father.links)
				father.lastRecordedLinks.add(l);
		}

	}

}
