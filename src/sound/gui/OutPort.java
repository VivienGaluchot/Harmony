package sound.gui;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import sound.gui.Types.Data;
import sound.math.Vector2D;

public class OutPort extends DataPort {

	private HashSet<DataLink> links;

	public OutPort(GraphObject father, Data dataType, String name) {
		super(father, dataType, name);
		links = new HashSet<>();
	}

	public Collection<DataLink> getLinks() {
		return Collections.unmodifiableCollection(links);
	}

	public void addLink(DataLink link) {
		links.add(link);
	}

	public void removeLink(DataLink link) {
		links.remove(link);
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
