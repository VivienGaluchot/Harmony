package harmony.gui.graph.elements;

import java.awt.Graphics;
import java.awt.Graphics2D;

import harmony.gui.Types.DataType;
import harmony.gui.graph.Record;
import harmony.math.Vector2D;

public class InPort extends Port {

	public InPort(Node father, DataType dataType, String name) {
		super(father, dataType, name);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g.create();
		Vector2D pos = getPos();

		g2d.setColor(getColor());
		g2d.drawString(name, (float) pos.x + 0.2f, (float) pos.y + 0.07f);

		g2d.dispose();
	}

	@Override
	public Record getCurrentRecord() {
		// TODO Auto-generated method stub
		return null;
	}
}
