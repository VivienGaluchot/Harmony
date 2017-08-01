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
import java.util.HashSet;
import java.util.Set;

import harmony.math.Vector2D;

public abstract class OutPort extends Port {

	public OutPort(Node father, Class<?> dataType, String name) {
		super(father, dataType, name);
	}
	
	public boolean containsComputingLoops() {
		return containsComputingLoopsRec(new HashSet<InPort>());
	}

	public boolean containsComputingLoopsRec(Set<InPort> inLoop) {
		Set<InPort> localDep = getDependencies();
		for (InPort ip : localDep) {
			if (inLoop.contains(ip))
				return true;
			inLoop.add(ip);
			if (ip.getDependencie() != null && ip.getDependencie().containsComputingLoopsRec(inLoop) == true)
				return true;
		}
		return false;
	}

	public abstract Set<InPort> getDependencies();

	@Override
	public abstract Object getValue();

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
