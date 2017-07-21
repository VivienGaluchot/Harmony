package sound.gui;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import sound.math.Vector2D;

public class GraphObject extends HCS {

	public Vector2D pos;
	public Vector2D size;
	public String name;

	private ArrayList<DataPort> inPorts;
	private ArrayList<DataPort> outPorts;

	public GraphObject() {
		super();
		name = "Default";
		pos = new Vector2D(-3. / 2, -1);
		size = new Vector2D(3, 2);

		inPorts = new ArrayList<>();
		outPorts = new ArrayList<>();

		inPorts.add(new DataPort(this, Types.Data.INTEGER, Types.IO.IN, "in1"));
		inPorts.add(new DataPort(this, Types.Data.FLOAT, Types.IO.IN, "in2"));
		inPorts.add(new DataPort(this, Types.Data.DOUBLE, Types.IO.IN, "in3"));
		outPorts.add(new DataPort(this, Types.Data.INTEGER, Types.IO.OUT, "out1"));
		outPorts.add(new DataPort(this, Types.Data.FLOAT, Types.IO.OUT, "out2"));
	}

	public boolean contains(Vector2D p) {
		return pos.x <= p.x && pos.x + size.x >= p.x && pos.y <= p.y && pos.y + size.y >= p.y;
	}

	public void showOpt(Component parent) {
		JOptionPane.showMessageDialog(parent, "Defaul object");
	}

	public Vector2D getPortPos(DataPort port) {
		int id;
		id = inPorts.indexOf(port);
		if (id >= 0) {
			return pos.add(new Vector2D(0.2, 1 + id * 0.4));
		}

		id = outPorts.indexOf(port);
		if (id >= 0) {
			return pos.add(new Vector2D(size.x - 0.2, 1 + id * 0.4));
		}

		return null;
	}

	public HCS getPointedObject(Vector2D p) {
		for (DataPort dp : inPorts) {
			HCS hcs = dp.getPointedObject(p);
			if (hcs != null)
				return dp;
		}
		for (DataPort dp : outPorts) {
			HCS hcs = dp.getPointedObject(p);
			if (hcs != null)
				return dp;
		}
		if (contains(p))
			return this;
		return null;
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setStroke(new BasicStroke(0.03f));

		g2d.setColor(getBackgroundColor());
		g2d.fill(new Rectangle2D.Double(pos.x, pos.y, size.x, size.y));

		g2d.setColor(getColor());
		g2d.draw(new Rectangle2D.Double(pos.x, pos.y, size.x, size.y));

		Font currentFont = new Font("Arial", Font.PLAIN, 1);
		Font newFont = currentFont.deriveFont(0.3f);
		g2d.setFont(newFont);
		g2d.drawString(name, (float) pos.x + 0.1f, (float) pos.y + 0.5f);

		// if(isSelected())
		// g2d.draw(new Rectangle2D.Double(pos.x, pos.y, size.x, size.y));

		newFont = currentFont.deriveFont(0.2f);
		g2d.setFont(newFont);

		for (DataPort p : inPorts)
			p.paint(g2d);
		for (DataPort p : outPorts)
			p.paint(g2d);

		g2d.dispose();
	}
}
