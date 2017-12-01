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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.CubicCurve2D;

import harmony.gui.Types;
import harmony.gui.Types.Command;
import harmony.gui.graph.Space;
import harmony.math.Vector2D;
import harmony.processcore.data.DataType;

public class Link extends GuiElement {

	private Space space;

	private Vector2D loosePoint;

	private OutPort outPort;
	private InPort inPort;

	private DataType type;

	private Area shape;
	private boolean showValue;
	private Double valueRadius;

	public Link(Space space, DataType type, OutPort start, InPort end) {
		super(space, Types.getDataColor(type), Types.getDataColor(type));
		this.space = space;

		this.type = type;
		if (start == null && end == null)
			throw new IllegalArgumentException();

		setOutPort(start);
		setInPort(end);

		shape = null;
		showValue = true;
		valueRadius = 0.2;
	}

	public DataType getDataType() {
		return type;
	}

	public OutPort getOutPort() {
		return outPort;
	}

	public void setOutPort(OutPort start) {
		assert type.includes(start.getDataType()) : "wrong port type";
		this.outPort = start;
	}

	public InPort getInPort() {
		return inPort;
	}

	public void setInPort(InPort end) {
		assert type.includes(end.getDataType()) : "wrong port type";
		this.inPort = end;
	}

	public void setLoosePoint(Vector2D p) {
		loosePoint = p;
	}

	public Object getValue() {
		if (outPort != null)
			return outPort.getValue();
		else
			return null;
	}

	@Override
	public Shape selectionShape() {
		return shape;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();

		Vector2D startPos;
		if (outPort != null)
			startPos = outPort.getPos().add(new Vector2D(outPort.radius, 0));
		else
			startPos = loosePoint;

		Vector2D endPos;
		if (inPort != null)
			endPos = inPort.getPos().add(new Vector2D(-inPort.radius, 0));
		else
			endPos = loosePoint;

		CubicCurve2D.Double cubicCurve;
		Double xDist = Math.max(0.3, Math.abs(endPos.subtract(startPos).x / 2));
		cubicCurve = new CubicCurve2D.Double(startPos.x, startPos.y, startPos.x + xDist, startPos.y, endPos.x - xDist,
				endPos.y, endPos.x, endPos.y);

		g2d.setStroke(new BasicStroke(0.04f));
		g2d.setColor(getCurrentColor());
		g2d.draw(cubicCurve);
		g2d.setStroke(new BasicStroke(0.1f));
		shape = new Area(g2d.getStroke().createStrokedShape(cubicCurve));

		if (showValue && startPos.subtract(endPos).getLength() > 2 * valueRadius) {
			Vector2D valuePos = endPos.subtract(startPos).multiply(0.5).add(startPos);
			Shape sp = getCenteredCircle(valuePos, valueRadius);
			g2d.setColor(getCurrentBackgroundColor());
			g2d.fill(sp);
			g2d.setColor(Color.WHITE);
			drawResizedHCenteredString(g2d, Types.getDataString(getValue()), valuePos, valueRadius * 1.7);
			shape.add(new Area(getCenteredCircle(valuePos, valueRadius + 0.05)));
		}

		g2d.dispose();
	}

	@Override
	public void handleCommand(Command c) {
		if (c == Types.Command.DELETE)
			space.removeLink(this);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Link) {
			Link dl = (Link) o;
			return dl.outPort == this.outPort && dl.inPort == this.inPort;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return outPort.hashCode() ^ inPort.hashCode();
	}
}
