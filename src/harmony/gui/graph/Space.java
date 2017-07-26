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

import harmony.gui.GraphPanel;
import harmony.gui.Types;
import harmony.gui.graph.elements.GuiElement;
import harmony.gui.graph.elements.InPort;
import harmony.gui.graph.elements.Link;
import harmony.gui.graph.elements.Node;
import harmony.gui.graph.elements.OutPort;
import harmony.gui.graph.elements.Port;
import harmony.math.Vector2D;

public class Space implements Recordable, MouseListener, MouseMotionListener, KeyListener {
	private GraphPanel panel;

	private ArrayList<Node> nodes;
	private ArrayList<Link> links;

	private Link draggedLink = null;

	private boolean didDrag = false;
	private Vector2D initMousePos = null;

	private GuiElement hovered = null;
	private GuiElement clicked = null;
	private GuiElement selected = null;

	private RecordQueue recordQueue;

	public Space(GraphPanel panel) {
		this.panel = panel;

		nodes = new ArrayList<>();
		links = new ArrayList<>();

		recordQueue = new RecordQueue();

		Node g1 = new Node(this);
		g1.pos = g1.pos.add(new Vector2D(-2, 0));
		addNode(g1);
		Node g2 = new Node(this);
		g2.pos = g2.pos.add(new Vector2D(2, 0));
		addNode(g2);
		Node g3 = new Node(this);
		g3.pos = g3.pos.add(new Vector2D(0, 3));
		addNode(g3);
	}

	// Objects

	public void addNode(Node n) {
		nodes.add(n);
	}

	public void removeNode(Node n) {
		for (Iterator<Link> iter = links.listIterator(); iter.hasNext();) {
			Link l = iter.next();
			if (l.start.father == n || l.end.father == n) {
				iter.remove();
			}
		}
		nodes.remove(n);
	}

	public void addLink(Link l) {
		for (Iterator<Link> iter = links.listIterator(); iter.hasNext();) {
			Link ol = iter.next();
			if (ol.end == l.end) {
				iter.remove();
			}
		}
		links.add(l);
	}

	public void removeLink(Link l) {
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

	public void trackChanges() {

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
					draggedLink = new Link(this, inPort.dataType, null, inPort);
				draggedLink.setClicked(true);
				draggedLink.setLoosePoint(vecMouse);

			} else if (clicked instanceof OutPort) {
				OutPort outPort = (OutPort) clicked;
				if (draggedLink == null)
					draggedLink = new Link(this, outPort.dataType, outPort, null);
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
		initMousePos = null;
		Vector2D vecMouse = panel.transformMousePosition(e.getPoint());
		GuiElement el = getPointedObject(vecMouse);
		if (el != null && el.isClicked() && didDrag == false)
			setSelected(el);
		else
			setSelected(null);
		setClicked(null);
		if (draggedLink != null) {
			if (el != null && el instanceof InPort) {
				InPort inPort = (InPort) el;
				if (draggedLink.end == null && inPort.dataType == draggedLink.dataType)
					draggedLink.end = inPort;
				if (draggedLink.start != null && draggedLink.end != null) {
					draggedLink.setClicked(false);
					addLink(draggedLink);
				}
			} else if (el != null && el instanceof OutPort) {
				OutPort outPort = (OutPort) el;
				if (draggedLink.start == null && outPort.dataType == draggedLink.dataType)
					draggedLink.start = outPort;
				if (draggedLink.start != null && draggedLink.end != null) {
					draggedLink.setClicked(false);
					addLink(draggedLink);
				}
			}
			draggedLink = null;
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

	// UNDO - REDO

	public void undo() {
		recordQueue.undo();
	}

	public void redo() {
		recordQueue.redo();
	}

	@Override
	public Record getCurrentRecord() {
		// TODO Auto-generated method stub
		return null;
	}

}
