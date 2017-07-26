package sound.gui.graph;

import java.awt.Graphics;
import java.awt.Graphics2D;

import sound.gui.Types;
import sound.gui.Types.DataType;
import sound.math.Vector2D;

public class InPort extends DataPort {

	private DataLink link;

	public InPort(GraphObject father, DataType dataType, String name) {
		super(father, dataType, name);
		link = null;
	}

	public DataLink getLink() {
		return link;
	}

	public void setLink(DataLink link) {
		this.link = link;
	}

	public void removeLink() {
		this.link = null;
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
