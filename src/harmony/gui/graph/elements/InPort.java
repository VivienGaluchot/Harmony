package harmony.gui.graph.elements;

import java.awt.Graphics;
import java.awt.Graphics2D;

import harmony.math.Vector2D;

public class InPort extends Port {
	private Link link;

	public InPort(Node father, Class<?> dataType, String name) {
		super(father, dataType, name);
		link = null;
	}

	@Override
	public Object getValue() {
		if (link == null)
			return null;
		Object v = link.getValue();
		if (v != null && v.getClass() != type)
			throw new IllegalArgumentException();
		return v;
	}

	public void setLink(Link link) {
		if (this.link != null) {
			Link t_link = this.link;
			this.link = null;
			t_link.remove();
		}
		if (link != null && link.type != this.type)
			throw new IllegalArgumentException();
		else
			this.link = link;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g.create();
		Vector2D pos = getPos();

		g2d.setColor(getColor());
		g2d.drawString(name, (float) pos.x + 0.2f, (float) pos.y + 0.07f);

		g2d.dispose();
	}
}
