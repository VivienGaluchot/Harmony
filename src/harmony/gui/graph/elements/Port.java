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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import harmony.data.DataProcessor;
import harmony.gui.Types;
import harmony.gui.Types.Command;
import harmony.math.Vector2D;

public abstract class Port extends GuiElement implements DataProcessor {
	public Node father;
	public Class<?> type;
	public String name;

	public Double radius;

	public Port(Node father, Class<?> type, String name) {
		super(Types.getDataColor(type), Types.getDataColor(type).darker());
		this.father = father;
		this.name = name;
		this.type = type;
		radius = 0.1;
	}

	public Vector2D getPos() {
		return father.getPortPos(this);
	}

	@Override
	public Class<?> getDataClass() {
		return type;
	}

	@Override
	public String getDataName() {
		return name;
	}

	@Override
	public boolean contains(Vector2D p) {
		Vector2D pos = getPos();
		Double selectionRadius = 1.5 * radius;
		Ellipse2D el = new Ellipse2D.Double(pos.x - selectionRadius, pos.y - selectionRadius, 2 * selectionRadius,
				2 * selectionRadius);
		return el.contains(p.x, p.y);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();

		Vector2D pos = getPos();
		Shape sp = new Ellipse2D.Double(pos.x - radius, pos.y - radius, 2 * radius, 2 * radius);

		if (!isHovered())
			g2d.setColor(getBackgroundColor());
		else
			g2d.setColor(Color.white);
		g2d.fill(sp);

		g2d.setColor(getColor());
		g2d.draw(sp);

		g2d.dispose();
	}

	@Override
	public void handleCommand(Command c) {
		return;
	}
}
