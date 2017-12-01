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

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import harmony.processcore.data.DataPattern;
import harmony.processcore.process.HrmProcess;

public class Node extends GuiElement implements Recordable, Persistable<Node> {

	private HrmProcess process;

	private ArrayList<InPort> inPorts;
	private ArrayList<OutPort> outPorts;

	private Vector2D pos;
	private Vector2D size;
	private Shape currentShape;

	public Node(Space space, HrmProcess process) {
		super(space);
		this.process = process;
		pos = new Vector2D(0, 0);
		size = new Vector2D(2, 2);

		inPorts = new ArrayList<>();
		DataPattern inPattern = process.getInputPattern();
		if (inPattern != null) {
			for (int i = 0; i < inPattern.size(); i++) {
				inPorts.add(new InPort(this, i, inPattern.getType(i), inPattern.getName(i)));
			}
		}

		outPorts = new ArrayList<>();
		DataPattern outPattern = process.getOutputPattern();
		if (outPattern != null) {
			for (int i = 0; i < outPattern.size(); i++) {
				outPorts.add(new OutPort(this, i, outPattern.getType(i), outPattern.getName(i)));
			}
		}

		adjustSize();
	}

	public String getName() {
		if (process != null)
			return process.getName();
		else
			return "_";
	}
	
	public Vector2D getPos() {
		return pos;
	}
	
	public void setPos(Vector2D pos) {
		this.pos = pos;
	}
	
	public Vector2D getSize() {
		return size;
	}

	public HrmProcess getProcess() {
		return process;
	}

	private void adjustSize() {
		size.y = 0.8 + Math.max(inPorts.size(), outPorts.size()) * 0.25;
	}

	public void remove() {
		setSelected(false);
		setHovered(false);
		setClicked(false);
		((Space) getFatherComponent()).removeNode(this);
	}
	
	@Override
	public Shape selectionShape() {
		Vector2D topLeft = getPos().subtract(getSize().multiply(0.5));
		Area a = new Area(new Rectangle2D.Double(topLeft.x - 0.05, topLeft.y - 0.05, size.x + 0.1, size.y + 0.1));
		for (GuiElement el : inPorts)
			a.add(new Area(el.selectionShape()));
		for (GuiElement el : outPorts)
			a.add(new Area(el.selectionShape()));
		return a;
	}

	public void showOpt() {
		ProcessUpdater updater = new ProcessUpdater(this, process);
		updater.update();
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

		Vector2D topLeft = getPos().subtract(getSize().multiply(0.5));
		currentShape = new Rectangle2D.Double(topLeft.x, topLeft.y, size.x, size.y);

		g2d.setColor(getCurrentBackgroundColor());
		g2d.fill(currentShape);

		g2d.setColor(getCurrentColor());
		g2d.draw(currentShape);

		Font currentFont = new Font("Arial", Font.PLAIN, 1);
		Font newFont = currentFont.deriveFont(0.3f);
		g2d.setFont(newFont);
		g2d.drawString(getName(), (float) topLeft.x + 0.1f, (float) topLeft.y + 0.4f);

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
