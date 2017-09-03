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

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Set;

import harmony.data.DataGenerator;
import harmony.data.DataProcessor;
import harmony.math.Vector2D;

public abstract class OutPort extends Port implements DataProcessor {

	public OutPort(Node node, Class<?> dataType, String name) {
		super(node, dataType, name);
	}

	@Override
	public abstract Set<DataGenerator> getDataProcessDependencies();

	@Override
	public abstract Object getData();

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g.create();
		Vector2D pos = getPos();

		g2d.setColor(getCurrentColor());
		FontMetrics fm = g2d.getFontMetrics(g2d.getFont());
		Rectangle2D nameRect = fm.getStringBounds(name, g2d);
		g2d.drawString(name, (float) pos.x - 0.2f - (float) nameRect.getWidth(), (float) pos.y + 0.07f);

		if (isHovered()) {
			if (this.getData() != null) {
				String dispMsg = this.getData().toString();
				Vector2D mspPos = pos.clone();
				g2d.drawString(dispMsg, (float) (mspPos.x + this.radius + 0.05f), (float) (mspPos.y - radius) + 0.05f);
			}
		}

		g2d.dispose();
	}
}
