package sound.gui;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import sound.math.Vector2D;

public class DataPort extends HCS  {
	public GraphObject father;
	public Types.Data dataType;
	public Types.IO ioType;
	public String name;
	
	public Double radius;
	
	public ArrayList<DataLink> links;

	public DataPort(GraphObject father, Types.Data dataType, Types.IO ioType, String name) {
		super(Types.getDataColor(dataType), Types.getDataColor(dataType).darker());
		this.father = father;
		this.name = name;
		this.dataType = dataType;
		this.ioType = ioType;
		radius = 0.1;
		links = new ArrayList<>();
	}

	public Vector2D getPos() {
		return father.getPortPos(this);
	}
	
	@Override
	public boolean contains(Vector2D p) {
		Vector2D pos = getPos();
		Ellipse2D el = new Ellipse2D.Double(pos.x - radius, pos.y - radius, 2 * radius, 2 * radius);
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
		
		FontMetrics fm = g2d.getFontMetrics(g2d.getFont());
		Rectangle2D nameRect = fm.getStringBounds(name, g2d);
		if (ioType == Types.IO.OUT)
			g2d.drawString(name, (float) pos.x - 0.2f - (float) nameRect.getWidth(), (float) pos.y + 0.07f);
		else
			g2d.drawString(name, (float) pos.x + 0.2f, (float) pos.y + 0.07f);
		
		for(DataLink gl : links)
			gl.paint(g2d);

		g2d.dispose();
	}
}
