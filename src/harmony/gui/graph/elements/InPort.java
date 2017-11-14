//    Harmony : procedural sound waves generator
//    Copyright (C) 2017  Vivien Galuchot
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, version 3 of the License.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

package harmony.gui.graph.elements;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import harmony.gui.Types;
import harmony.math.Vector2D;
import harmony.processcore.data.DataType;

public class InPort extends Port {
	private Link link;

	public InPort(Node node, DataType dataType, String name) {
		super(node, dataType, name);
		link = null;
	}

	public void setLink(Link link) {
		assert link == null || link.type.equals(this.type) : "wrong link type";
		this.link = link;
	}

	public Link getLink() {
		return link;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g.create();
		Vector2D pos = getPos();

		g2d.setColor(getCurrentColor());
		g2d.drawString(name, (float) pos.x + 0.2f, (float) pos.y + 0.07f);

		if (isHovered()) {
			Object data = "_";
			if (data != null) {
				String dispMsg = Types.getDataString(data);
				Rectangle2D bound = g2d.getFontMetrics().getStringBounds(dispMsg, g2d);
				Vector2D mspPos = pos.clone();
				g2d.drawString(dispMsg, (float) (mspPos.x - bound.getWidth() - this.radius - 0.05f),
						(float) (mspPos.y - radius) + 0.05f);
			}
		}

		g2d.dispose();
	}
}
