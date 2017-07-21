package sound.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import sound.math.Vector2D;

public class GraphLink extends HCS {

	public Vector2D loosePoint;
	public DataPort start;
	public DataPort end;

	public Types.Data dataType;

	public GraphLink(Types.Data dataType, DataPort start, DataPort end) {
		super(Color.white, Types.getDataColor(dataType));
		this.dataType = dataType;
		if(start == null && end == null)
			throw new IllegalArgumentException();
		this.start = start;
		this.end = end;
	}

	public void setLoosePoint(Vector2D p) {
		loosePoint = p;
	}
	
	public boolean contains(Vector2D p) {
		// TODO
		return false;
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();

		Vector2D startPos;
		if (start != null)
			startPos = start.getPos();
		else
			startPos = loosePoint;

		Vector2D endPos;
		if (end != null)
			endPos = end.getPos();
		else
			endPos = loosePoint;

		Line2D line = new Line2D.Double(startPos.x, startPos.y, endPos.x, endPos.y);
		
		g2d.setColor(getColor());
		g2d.draw(line);

		g2d.dispose();
	}
}
