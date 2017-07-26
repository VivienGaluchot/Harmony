package sound.gui.graph;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import sound.gui.HCS;
import sound.gui.Types;
import sound.gui.Types.Command;
import sound.math.Vector2D;

public class GraphSpace extends ArrayList<GraphObject>
		implements MouseListener, MouseMotionListener, KeyListener {
	private static final long serialVersionUID = 1L;

	private GraphPanel panel;

	private DataLink draggedLink = null;
	
	private boolean didDrag;
	private Vector2D initMousePos;

	private HCS hovered = null;
	private HCS clicked = null;
	private HCS selected = null;

	public GraphSpace(GraphPanel panel) {
		this.panel = panel;

		initMousePos = null;
		
		GraphObject g1 = new GraphObject(this);
		g1.pos = g1.pos.add(new Vector2D(-2, 0));
		add(g1);
		GraphObject g2 = new GraphObject(this);
		g2.pos = g2.pos.add(new Vector2D(2, 0));
		add(g2);
		GraphObject g3 = new GraphObject(this);
		g3.pos = g3.pos.add(new Vector2D(0, 3));
		add(g3);
	}

	public Deque<HCS> getObjectDeque() {
		ArrayDeque<HCS> deque = new ArrayDeque<>();
		for (GraphObject go : this) {
			deque.add(go);
			for (DataPort dp : go.getInPorts())
				deque.add(dp);
			for (DataPort dp : go.getOutPorts())
				deque.add(dp);
		}

		for (GraphObject go : this) {
			for (InPort dp : go.getInPorts())
				if (dp.getLink() != null)
					deque.add(dp.getLink());
		}
		return deque;
	}

	public HCS getPointedObject(Vector2D p) {
		Deque<HCS> deque = getObjectDeque();
		HCS hcs;
		do {
			hcs = deque.pollLast();
			if (hcs != null && hcs.contains(p))
				return hcs;
		} while (hcs != null);
		return null;
	}

	public synchronized void paint(Graphics g) {
		for (HCS hcs : getObjectDeque())
			hcs.paint(g);

		if (draggedLink != null)
			draggedLink.paint(g);
	}

	private void setHovered(HCS hovered) {
		if (this.hovered != null) {
			this.hovered.setHovered(false);
		}
		this.hovered = hovered;
		if (this.hovered != null) {
			this.hovered.setHovered(true);
		}
	}

	private void setClicked(HCS clicked) {
		if (this.clicked != null) {
			this.clicked.setClicked(false);
		}
		this.clicked = clicked;
		if (this.clicked != null) {
			this.clicked.setClicked(true);
		}
	}

	private void setSelected(HCS selected) {
		if (this.selected != null) {
			this.selected.setSelected(false);
		}
		this.selected = selected;
		if (this.selected != null) {
			this.selected.setSelected(true);
		}
	}

	// Listeners

	// Mouse

	@Override
	public void mouseDragged(MouseEvent e) {
		Vector2D vecMouse = panel.transformMousePosition(e.getPoint());
		if (initMousePos != null) {
			if (clicked instanceof GraphObject) {
				GraphObject go = (GraphObject) clicked;
				go.pos = go.pos.add(vecMouse.subtract(initMousePos));
				initMousePos = vecMouse;
			} else if (clicked instanceof InPort) {
				InPort inPort = (InPort) clicked;
				if (draggedLink == null)
					draggedLink = new DataLink(inPort.dataType, null, inPort);
				draggedLink.setClicked(true);
				draggedLink.setLoosePoint(vecMouse);

			} else if (clicked instanceof OutPort) {
				OutPort outPort = (OutPort) clicked;
				if (draggedLink == null)
					draggedLink = new DataLink(outPort.dataType, outPort, null);
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
		HCS hcs = getPointedObject(vecMouse);
		if (e.getButton() == MouseEvent.BUTTON1) {
			setClicked(hcs);
			initMousePos = panel.transformMousePosition(e.getPoint());
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			if (hcs instanceof GraphObject) {
				GraphObject go = (GraphObject) hcs;
				go.showOpt(panel);
			}
		}
		didDrag = false;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		initMousePos = null;
		Vector2D vecMouse = panel.transformMousePosition(e.getPoint());
		HCS hcs = getPointedObject(vecMouse);
		if (hcs != null && hcs.isClicked() && didDrag == false)
			setSelected(hcs);
		else
			setSelected(null);
		setClicked(null);
		if (draggedLink != null) {
			if (hcs != null && hcs instanceof InPort) {
				InPort inPort = (InPort) hcs;
				if (draggedLink.end == null && inPort.dataType == draggedLink.dataType)
					draggedLink.end = inPort;
				if (draggedLink.start != null && draggedLink.end != null) {
					draggedLink.setClicked(false);
					draggedLink.start.addLink(draggedLink);
					draggedLink.end.setLink(draggedLink);
				}
			} else if (hcs != null && hcs instanceof OutPort) {
				OutPort outPort = (OutPort) hcs;
				if (draggedLink.start == null && outPort.dataType == draggedLink.dataType)
					draggedLink.start = outPort;
				if (draggedLink.start != null && draggedLink.end != null) {
					draggedLink.setClicked(false);
					draggedLink.start.addLink(draggedLink);
					draggedLink.end.setLink(draggedLink);
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

}
