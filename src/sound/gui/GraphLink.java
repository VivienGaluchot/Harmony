package sound.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class GraphLink extends HCS {
	
	public DataPort start;
	public DataPort end;
	
	public DataType dataType;
	
	public GraphLink(DataType dataType, DataPort start, DataPort end) {
		super();
		this.dataType = dataType;
		this.start = start;
		this.end = end;
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		
		g2d.draw(new Line2D.Double(start.getPos().x, start.getPos().y, end.getPos().x, end.getPos().y));
		
		g2d.dispose();
	}
}
