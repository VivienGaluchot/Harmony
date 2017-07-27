package harmony.gui.graph.elements;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.CubicCurve2D;

import harmony.gui.Types;
import harmony.gui.Types.Command;
import harmony.gui.graph.Space;
import harmony.math.Vector2D;

public class Link<T> extends GuiElement {

	public Space space;

	public Vector2D loosePoint;
	public OutPort<T> start;
	public InPort<T> end;

	public Types.DataType dataType;

	private Shape shape;

	public Link(Space space, Types.DataType dataType, OutPort<T> start, InPort<T> end) {
		super(Types.getDataColor(dataType), Types.getDataColor(dataType));
		this.space = space;
		
		this.dataType = dataType;
		if (start == null && end == null)
			throw new IllegalArgumentException();
		this.start = start;
		this.end = end;
		shape = null;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Link) {
			Link<T> dl = (Link<T>) o;
			if (dl.start == this.start && dl.end == this.end)
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return start.hashCode() ^ end.hashCode();
	}

	public void remove() {
		space.removeLink(this);
	}

	public void setLoosePoint(Vector2D p) {
		loosePoint = p;
	}

	@Override
	public boolean contains(Vector2D p) {
		return shape != null && shape.contains(p.x, p.y);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();

		Vector2D startPos;
		if (start != null)
			startPos = start.getPos().add(new Vector2D(start.radius, 0));
		else
			startPos = loosePoint;

		Vector2D endPos;
		if (end != null)
			endPos = end.getPos().add(new Vector2D(-end.radius, 0));
		else
			endPos = loosePoint;

		CubicCurve2D.Double cubicCurve; // Cubic curve
		Double xDist = Math.max(0.3, endPos.subtract(startPos).x / 2);
		cubicCurve = new CubicCurve2D.Double(startPos.x, startPos.y, startPos.x + xDist, startPos.y, endPos.x - xDist,
				endPos.y, endPos.x, endPos.y);

		g2d.setStroke(new BasicStroke(0.04f));
		g2d.setColor(getColor());
		g2d.draw(cubicCurve);
		g2d.setStroke(new BasicStroke(0.1f));
		shape = g2d.getStroke().createStrokedShape(cubicCurve);

		if (isSelected()) {
			float dash1[] = { 0.1f };
			BasicStroke dashed = new BasicStroke(0.01f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1f, dash1, 0.0f);
			g2d.setStroke(dashed);
			g2d.draw(shape);
		}

		g2d.dispose();
	}

	@Override
	public void handleCommand(Command c) {
		if (c == Types.Command.DELETE)
			remove();
	}
	
}
