package sound.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import sound.gui.Types.Command;
import sound.math.Vector2D;

public class DataPort extends HCS {
	public GraphObject father;
	public Types.Data dataType;
	public String name;

	public Double radius;

	public DataPort(GraphObject father, Types.Data dataType, String name) {
		super(Types.getDataColor(dataType), Types.getDataColor(dataType).darker());
		this.father = father;
		this.name = name;
		this.dataType = dataType;
		radius = 0.1;
	}

	public Vector2D getPos() {
		return father.getPortPos(this);
	}

	@Override
	public boolean contains(Vector2D p) {
		Vector2D pos = getPos();
		Double selectionRadius = 2 * radius;
		Ellipse2D el = new Ellipse2D.Double(pos.x - selectionRadius, pos.y - selectionRadius, 2 * selectionRadius,
				2 * selectionRadius);
		return el.contains(p.x, p.y);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();

		Vector2D pos = getPos();
		Shape sp = new Ellipse2D.Double(pos.x - radius, pos.y - radius, 2 * radius, 2 * radius);

		g2d.setColor(getBackgroundColor());
		g2d.fill(sp);

		g2d.setColor(getColor());
		g2d.draw(sp);

		g2d.dispose();
	}

	@Override
	public void handleCommand(Command c) {
		return;
	}
}
