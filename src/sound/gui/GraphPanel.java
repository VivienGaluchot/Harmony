package sound.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

import sound.math.Vector2D;

public class GraphPanel extends JPanel
		implements MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener {
	private static final long serialVersionUID = 1L;

	private GraphSpace space;

	private AffineTransform currentTransform;

	private boolean displayGrid;
	private float gridSize;

	private Vector2D initMousePos;
	private Vector2D afterTranslate;
	private float afterScale;

	private int graphicSize;

	private Color mainGridColor = new Color(230, 230, 230);
	private Color subGridColor = new Color(240, 240, 240);
	private Color backgroundColor = new Color(250, 250, 250);

	private HCS hovered = null;
	private HCS clicked = null;
	private HCS selected = null;

	private DataLink draggedLink = null;

	public GraphPanel() {
		super();

		space = new GraphSpace();

		currentTransform = new AffineTransform();
		initMousePos = null;
		afterTranslate = new Vector2D(0, 0);
		afterScale = 1;

		graphicSize = 10;

		displayGrid = true;
		gridSize = 5;

		setBackground(backgroundColor);

		setFocusable(true);
		requestFocus();
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		addComponentListener(this);
	}

	public boolean isDisplayGrid() {
		return displayGrid;
	}

	public void setDisplayGrid(boolean displayGrid) {
		this.displayGrid = displayGrid;
	}

	public float getGridSize() {
		return gridSize;
	}

	public void setGridSize(float gridSize) {
		if (gridSize <= 0)
			throw new IllegalArgumentException("gridSize have to be strictly positive");
		this.gridSize = gridSize;
	}

	public void computeTransform() {
		double scale = Math.min(getWidth(), getHeight()) / (double) graphicSize;
		currentTransform.setToIdentity();
		currentTransform.scale(scale, scale);
		currentTransform.translate(getWidth() / (2 * scale), getHeight() / (2 * scale));

		currentTransform.scale(afterScale, afterScale);
		currentTransform.translate(afterTranslate.x, afterTranslate.y);
	}

	public void setTranslate(Vector2D translate) {
		this.afterTranslate = translate;
		computeTransform();
	}

	public void zoom(int unit) {
		while (unit > 0) {
			afterScale = afterScale / 1.1f;
			unit--;
		}
		while (unit < 0) {
			afterScale = afterScale * 1.1f;
			unit++;
		}
		computeTransform();
	}

	public synchronized void setGraphicSize(int drawSize) {
		this.graphicSize = drawSize;
		computeTransform();
		repaint();
	}

	public Vector2D transformMousePosition(Vector2D position) {
		try {
			Point2D.Double p = new Point2D.Double(position.x, position.y);
			currentTransform.inverseTransform(p, p);
			return new Vector2D(p);
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Vector2D transformMousePosition(Point position) {
		return transformMousePosition(new Vector2D(position));
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

	// Animable

	@Override
	public synchronized void paint(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g2d.transform(currentTransform);

		Font currentFont = new Font("Arial", Font.PLAIN, 1);
		g2d.setFont(currentFont.deriveFont(0.8f));

		if (displayGrid) {
			Point2D.Float topLeft = new Point2D.Float(0, 0);
			Point2D.Float botRight = new Point2D.Float(getWidth(), getHeight());
			try {
				currentTransform.inverseTransform(topLeft, topLeft);
				currentTransform.inverseTransform(botRight, botRight);
				float x = 0;
				float y = 0;
				x = topLeft.x;
				y = topLeft.y;

				float littleGridSize = gridSize / 5;
				x = topLeft.x;
				y = topLeft.y;
				x = Math.round(x / littleGridSize) * littleGridSize;
				y = Math.round(y / littleGridSize) * littleGridSize;
				g2d.setColor(subGridColor);
				g2d.setStroke(new BasicStroke(0.03f));
				while (x < botRight.x) {
					Line2D line = new Line2D.Float(x, topLeft.y, x, botRight.y);
					g2d.draw(line);
					x += littleGridSize;
				}
				while (y < botRight.y) {
					Line2D line = new Line2D.Float(topLeft.x, y, botRight.x, y);
					g2d.draw(line);
					y += littleGridSize;
				}

				x = topLeft.x;
				y = topLeft.y;
				x = Math.round(x / gridSize) * gridSize;
				y = Math.round(y / gridSize) * gridSize;
				g2d.setColor(mainGridColor);
				g2d.setStroke(new BasicStroke(0.04f));
				while (x < botRight.x) {
					Line2D line = new Line2D.Float(x, topLeft.y, x, botRight.y);
					g2d.draw(line);
					x += gridSize;
				}
				while (y < botRight.y) {
					Line2D line = new Line2D.Float(topLeft.x, y, botRight.x, y);
					g2d.draw(line);
					y += gridSize;
				}
			} catch (NoninvertibleTransformException e) {
			}
		}

		g2d.setStroke(new BasicStroke(0.03f));
		g2d.setFont(currentFont.deriveFont(0.2f));
		space.paint(g2d);

		if (draggedLink != null)
			draggedLink.paint(g2d);

		g2d.dispose();
	}

	// Mouse

	@Override
	public void mouseDragged(MouseEvent e) {
		Vector2D vecMouse = transformMousePosition(e.getPoint());
		if (initMousePos != null) {
			if (clicked == null) {
				afterTranslate = afterTranslate.add(vecMouse.subtract(initMousePos));
				setTranslate(afterTranslate);
			} else if (clicked instanceof GraphObject) {
				GraphObject go = (GraphObject) clicked;
				go.pos = go.pos.add(vecMouse.subtract(initMousePos));
				initMousePos = vecMouse;
			} else if (clicked instanceof DataPort) {
				DataPort dp = (DataPort) clicked;
				if (draggedLink == null) {
					if (dp.ioType == Types.IO.OUT)
						draggedLink = new DataLink(dp.dataType, dp, null);
					else if (dp.ioType == Types.IO.IN)
						draggedLink = new DataLink(dp.dataType, null, dp);
				}
				draggedLink.setClicked(true);
				draggedLink.setLoosePoint(vecMouse);

			}
		}
		setHovered(space.getPointedObject(vecMouse));
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Vector2D vecMouse = transformMousePosition(e.getPoint());
		setHovered(space.getPointedObject(vecMouse));
		repaint();
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
		Vector2D vecMouse = transformMousePosition(e.getPoint());
		HCS hcs = space.getPointedObject(vecMouse);
		if (e.getButton() == MouseEvent.BUTTON1) {
			setClicked(hcs);
			initMousePos = transformMousePosition(e.getPoint());
			repaint();
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			if (hcs instanceof GraphObject) {
				GraphObject go = (GraphObject) hcs;
				go.showOpt(this);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		initMousePos = null;
		Vector2D vecMouse = transformMousePosition(e.getPoint());
		HCS hcs = space.getPointedObject(vecMouse);
		if (hcs != null && hcs.isClicked())
			setSelected(hcs);
		else
			setSelected(null);
		setClicked(null);
		if (draggedLink != null) {
			if (hcs != null && hcs instanceof DataPort) {
				DataPort dp = (DataPort) hcs;
				if (draggedLink.start == null && dp.ioType == Types.IO.OUT && dp.dataType == draggedLink.dataType) {
					draggedLink.start = dp;
				} else if (draggedLink.end == null && dp.ioType == Types.IO.IN) {
					draggedLink.end = dp;
				}
				if (draggedLink.start != null && draggedLink.end != null && dp.dataType == draggedLink.dataType) {
					draggedLink.setClicked(false);
					draggedLink.start.links.add(draggedLink);
					draggedLink.end.links.add(draggedLink);
				}
			}
			draggedLink = null;
		}
		mouseMoved(e);
		repaint();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int rotation = e.getWheelRotation();
		zoom(rotation);
		mouseMoved(e);
		repaint();
	}

	// Component Listener

	@Override
	public void componentHidden(ComponentEvent arg0) {
		computeTransform();
		repaint();
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		computeTransform();
		repaint();
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		computeTransform();
		repaint();
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		computeTransform();
		repaint();
	}
}
