package harmony.gui.graph.elements;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import harmony.gui.Types.DataType;
import harmony.math.Vector2D;

public class OutPort<T> extends Port<T> {

	public OutPort(Node father, DataType dataType, String name) {
		super(father, dataType, name);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g.create();
		Vector2D pos = getPos();

		g2d.setColor(getColor());
		FontMetrics fm = g2d.getFontMetrics(g2d.getFont());
		Rectangle2D nameRect = fm.getStringBounds(name, g2d);
		g2d.drawString(name, (float) pos.x - 0.2f - (float) nameRect.getWidth(), (float) pos.y + 0.07f);

		g2d.dispose();
	}
}
