package sound.gui;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import sound.gui.Types.Command;
import sound.math.Vector2D;

public class GraphObject extends HCS {

	public GraphSpace space;

	public Vector2D pos;
	public Vector2D size;
	public String name;

	private ArrayList<InPort> inPorts;
	private ArrayList<OutPort> outPorts;

	public GraphObject(GraphSpace space) {
		super();
		this.space = space;
		name = "Default";
		pos = new Vector2D(-3. / 2, -1);
		size = new Vector2D(3, 2);

		inPorts = new ArrayList<>();
		outPorts = new ArrayList<>();

		inPorts.add(new InPort(this, Types.Data.INTEGER, "in1"));
		inPorts.add(new InPort(this, Types.Data.FLOAT, "in2"));
		inPorts.add(new InPort(this, Types.Data.DOUBLE, "in3"));
		outPorts.add(new OutPort(this, Types.Data.INTEGER, "out1"));
		outPorts.add(new OutPort(this, Types.Data.FLOAT, "out2"));

		adjustSize();
	}

	public void adjustSize() {
		size.y = Math.max(1 + (inPorts.size() * 0.4), 1 + (outPorts.size() * 0.4));
	}

	public void remove() {
		space.remove(this);
		for (InPort dp : inPorts)
			dp.removeLink();
		for (OutPort dp : outPorts)
			for (DataLink dl : dp.getLinks())
				dl.remove();
	}

	@Override
	public boolean contains(Vector2D p) {
		return pos.x <= p.x && pos.x + size.x >= p.x && pos.y <= p.y && pos.y + size.y >= p.y;
	}

	public void showOpt(Component parent) {
		JOptionPane.showMessageDialog(parent, "Defaul object");
	}

	public List<InPort> getInPorts() {
		return Collections.unmodifiableList(inPorts);
	}

	public List<OutPort> getOutPorts() {
		return Collections.unmodifiableList(outPorts);
	}

	public Vector2D getPortPos(DataPort port) {
		int id;
		id = inPorts.indexOf(port);
		if (id >= 0) {
			return pos.add(new Vector2D(0, 1 + id * 0.4));
		}

		id = outPorts.indexOf(port);
		if (id >= 0) {
			return pos.add(new Vector2D(size.x, 1 + id * 0.4));
		}

		return null;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setColor(getBackgroundColor());
		g2d.fill(new Rectangle2D.Double(pos.x, pos.y, size.x, size.y));

		g2d.setColor(getColor());
		g2d.draw(new Rectangle2D.Double(pos.x, pos.y, size.x, size.y));

		Font currentFont = new Font("Arial", Font.PLAIN, 1);
		Font newFont = currentFont.deriveFont(0.3f);
		g2d.setFont(newFont);
		g2d.drawString(name, (float) pos.x + 0.1f, (float) pos.y + 0.5f);
		if (isSelected()) {
			float dash1[] = { 0.1f };
			BasicStroke dashed = new BasicStroke(0.01f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1f, dash1, 0.0f);
			g2d.setStroke(dashed);
			g2d.draw(new Rectangle2D.Double(pos.x - 0.1, pos.y - 0.1, size.x + 0.2, size.y + 0.2));
		}

		g2d.dispose();
	}

	@Override
	public void handleCommand(Command c) {
		if (c == Types.Command.DELETE)
			remove();
	}
}
