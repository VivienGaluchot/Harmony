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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import harmony.gui.Types;
import harmony.gui.Types.Command;
import harmony.gui.graph.Space;
import harmony.gui.persist.Persistable;
import harmony.gui.persist.Persistor;
import harmony.gui.persist.persistors.NodePersistor;
import harmony.gui.record.ChangeRecord;
import harmony.gui.record.Recordable;
import harmony.gui.record.StateRecord;
import harmony.math.Vector2D;

public class Node extends GuiElement implements Recordable, Persistable<Node> {

	public Vector2D pos;

	private Vector2D size;
	private String name;

	private ArrayList<InPort> inPorts;
	private ArrayList<OutPort> outPorts;

	private Shape currentShape;

	public Node() {
		this(null, null);
	}

	public Node(Space space, String name) {
		super(space);
		this.name = name;
		pos = new Vector2D(0, 0);
		size = new Vector2D(2, 2);

		inPorts = new ArrayList<>();
		outPorts = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected void addInPort(InPort ip) {
		inPorts.add(ip);
		adjustSize();
	}

	protected void addOutPort(OutPort op) {
		outPorts.add(op);
		adjustSize();
	}

	protected void adjustSize() {
		size.y = 0.8 + Math.max(inPorts.size(), outPorts.size()) * 0.25;
	}

	public void remove() {
		setSelected(false);
		setHovered(false);
		setClicked(false);
		((Space) getFatherComponent()).removeNode(this);
	}

	@Override
	public boolean contains(Vector2D p) {
		if (currentShape != null)
			return currentShape.contains(p.x, p.y);
		else
			return false;
	}

	public void showOpt() {
		JOptionPane.showMessageDialog(getFatherComponent(), "Empty node");
	}

	public List<InPort> getInPorts() {
		return Collections.unmodifiableList(inPorts);
	}

	public List<OutPort> getOutPorts() {
		return Collections.unmodifiableList(outPorts);
	}

	public Vector2D getPortPos(Port port) {
		int id;
		Vector2D topLeft = new Vector2D(pos.x - size.x / 2.0, pos.y - size.y / 2.0);

		id = inPorts.indexOf(port);
		if (id >= 0)
			return topLeft.add(new Vector2D(0, 0.75 + id * 0.25));

		id = outPorts.indexOf(port);
		if (id >= 0)
			return topLeft.add(new Vector2D(size.x, 0.75 + id * 0.25));

		return null;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();

		Vector2D topLeft = new Vector2D(pos.x - size.x / 2.0, pos.y - size.y / 2.0);
		currentShape = new Rectangle2D.Double(topLeft.x, topLeft.y, size.x, size.y);

		g2d.setColor(getCurrentBackgroundColor());
		g2d.fill(currentShape);

		g2d.setColor(getCurrentColor());
		g2d.draw(currentShape);

		Font currentFont = new Font("Arial", Font.PLAIN, 1);
		Font newFont = currentFont.deriveFont(0.3f);
		g2d.setFont(newFont);
		g2d.drawString(name, (float) topLeft.x + 0.1f, (float) topLeft.y + 0.4f);
		if (isSelected()) {
			float dash1[] = { 0.1f };
			BasicStroke dashed = new BasicStroke(0.01f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1f, dash1, 0.0f);
			g2d.setStroke(dashed);
			g2d.draw(new Rectangle2D.Double(topLeft.x - 0.1, topLeft.y - 0.1, size.x + 0.2, size.y + 0.2));
		}

		g2d.dispose();
	}

	@Override
	public void handleCommand(Command c) {
		if (c == Types.Command.DELETE)
			remove();
	}

	// Records

	@Override
	public StateRecord getCurrentState() {
		return new NodeStateRecord(this);
	}

	public class NodeStateRecord extends StateRecord {
		private Vector2D pos;

		public NodeStateRecord(Node father) {
			super(father);
			pos = father.pos;
		}

		@Override
		public ChangeRecord getDiffs(StateRecord ref) {
			if (pos.equals(((NodeStateRecord) ref).pos)) {
				return null;
			} else {
				return new NodeChangeRecord((Node) getFather(), pos, ((NodeStateRecord) ref).pos);
			}
		}
	}

	public class NodeChangeRecord extends ChangeRecord {
		private Vector2D diffPos;

		public NodeChangeRecord(Node father, Vector2D startPos, Vector2D endPos) {
			super(father);
			this.diffPos = endPos.subtract(startPos);
		}

		@Override
		public void undoChange() {
			((Node) getFather()).pos = ((Node) getFather()).pos.subtract(diffPos);
		}

		@Override
		public void redoChange() {
			((Node) getFather()).pos = ((Node) getFather()).pos.add(diffPos);
		}
	}

	// Persistence

	@Override
	public Persistor<Node> getCurrentPersistRecord() {
		return new NodePersistor(this);
	}
}
