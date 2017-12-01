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
import java.awt.Shape;

import harmony.gui.Types;
import harmony.gui.Types.Command;
import harmony.math.Vector2D;
import harmony.processcore.data.DataType;

public abstract class Port extends GuiElement {
	protected Node node;
	private int id;
	protected DataType type;
	protected String name;

	protected Double radius;

	public Port(Node node, int id, DataType type, String name) {
		super(node, Types.getDataColor(type), Types.getDataColor(type).darker());
		this.node = node;
		this.id = id;
		this.type = type;
		this.name = name;
		radius = 0.1;
	}
	
	abstract Object getValue();

	public Vector2D getPos() {
		return node.getPortPos(this);
	}

	public DataType getDataType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	public Node getNode() {
		return node;
	}
	
	public int getId() {
		return id;
	}

	@Override
	public Shape selectionShape() {
		return getCenteredCircle(getPos(), radius + 0.05);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();

		Shape sp = getCenteredCircle(getPos(), radius);
		g2d.setColor(getCurrentBackgroundColor());
		g2d.fill(sp);
		g2d.setColor(getCurrentColor());
		g2d.draw(sp);

		g2d.dispose();
	}

	@Override
	public void handleCommand(Command c) {
		return;
	}
}
