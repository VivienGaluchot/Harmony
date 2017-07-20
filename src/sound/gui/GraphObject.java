package sound.gui;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import sound.math.Vector2D;

public class GraphObject extends HCS {

	public Vector2D pos;
	public Vector2D size;
	public String name;

	public GraphObject() {
		super();
		name = "Default";
		pos = new Vector2D(-3. / 2, -1);
		size = new Vector2D(3, 2);
	}

	public boolean contains(Vector2D p) {
		return pos.x <= p.x && pos.x + size.x >= p.x && pos.y <= p.y && pos.y + size.y >= p.y;
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		
		g2d.setStroke(new BasicStroke(0.03f));

		if (isClicked())
			g2d.setColor(clickedBackgroundColor);
		else if (isHovered())
			g2d.setColor(hoveredBackgroundColor);
		else
			g2d.setColor(backgroundColor);
		g2d.fill(new Rectangle2D.Double(pos.x, pos.y, size.x, size.y));
		
		if (isClicked())
			g2d.setColor(clickedColor);
		else if (isHovered())
			g2d.setColor(hoveredColor);
		else
			g2d.setColor(color);
		g2d.draw(new Rectangle2D.Double(pos.x, pos.y, size.x, size.y));

		g2d.setColor(color);
		Font currentFont = new Font("Consolas", Font.PLAIN, 1);
		Font newFont = currentFont.deriveFont(0.5f);
		g2d.setFont(newFont);
		g2d.drawString(name, (float) pos.x, (float) pos.y);

		// if(isSelected())
		// g2d.draw(new Rectangle2D.Double(pos.x, pos.y, size.x, size.y));
		
		g2d.dispose();
	}
}
