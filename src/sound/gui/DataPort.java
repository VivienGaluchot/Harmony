package sound.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import sound.math.Vector2D;

public class DataPort extends HCS  {
	public GraphObject father;
	public DataType dataType;
	public String name;
	
	private static Double radius = 0.1;

	public DataPort(GraphObject father, DataType dataType, String name) {
		super();
		this.father = father;
		this.name = name;
		this.dataType = dataType;
	}

	public Vector2D getPos() {
		return father.getPortPos(this);
	}
	
	public boolean contains(Vector2D p) {
		Vector2D pos = getPos();
		Ellipse2D el = new Ellipse2D.Double(pos.x - radius, pos.y - radius, 2 * radius, 2 * radius);
		return el.contains(p.x, p.y);
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		
		Vector2D pos = getPos();
		Shape sp = new Ellipse2D.Double(pos.x - radius, pos.y - radius, 2 * radius, 2 * radius);

		g2d.setColor(getBackgroundColor());
		g2d.fill(sp);
		
		g2d.setColor(getColor());
		g2d.draw(sp);
		
		g2d.drawString(name, (float) pos.x + 0.2f, (float) pos.y + 0.07f);

		g2d.dispose();
	}
}
