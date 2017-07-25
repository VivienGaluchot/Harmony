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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
		implements ComponentListener, MouseWheelListener, MouseListener, MouseMotionListener, KeyListener {
	private static final long serialVersionUID = 1L;

	private GraphSpace space;

	private AffineTransform currentTransform;

	private boolean displayGrid;
	private float gridSize;

	private Vector2D afterTranslate;
	private float afterScale;

	private int graphicSize;

	private Vector2D initMousePos;

	private Color mainGridColor = new Color(230, 230, 230);
	private Color subGridColor = new Color(240, 240, 240);
	private Color backgroundColor = new Color(250, 250, 250);

	public GraphPanel() {
		super();

		space = new GraphSpace(this);

		currentTransform = new AffineTransform();
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
		addKeyListener(this);
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

		g2d.dispose();
	}

	// MouseWheel Listener

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int rotation = e.getWheelRotation();
		zoom(rotation);
		space.mouseMoved(e);
		repaint();
	}

	// Component Listener

	@Override
	public void componentHidden(ComponentEvent e) {
		computeTransform();
		repaint();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		computeTransform();
		repaint();
	}

	@Override
	public void componentResized(ComponentEvent e) {
		computeTransform();
		repaint();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		computeTransform();
		repaint();
	}

	// KeyListener

	@Override
	public void keyPressed(KeyEvent e) {
		space.keyPressed(e);
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		space.keyReleased(e);
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		space.keyTyped(e);
		repaint();
	}

	// MouseListener

	@Override
	public void mouseDragged(MouseEvent e) {
		Vector2D vecMouse = transformMousePosition(e.getPoint());
		if (initMousePos != null) {
			afterTranslate = afterTranslate.add(vecMouse.subtract(initMousePos));
			setTranslate(afterTranslate);
		}
		space.mouseDragged(e);
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		space.mouseMoved(e);
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		space.mouseClicked(e);
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		space.mouseEntered(e);
		repaint();

	}

	@Override
	public void mouseExited(MouseEvent e) {
		space.mouseExited(e);
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Vector2D vecMouse = transformMousePosition(e.getPoint());
		if ((e.getButton() == MouseEvent.BUTTON1) && (space.getPointedObject(vecMouse) == null))
			initMousePos = vecMouse;
		space.mousePressed(e);
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		initMousePos = null;
		space.mouseReleased(e);
		repaint();
	}
}
