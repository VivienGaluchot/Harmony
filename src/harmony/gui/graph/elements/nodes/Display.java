package harmony.gui.graph.elements.nodes;

import java.awt.Graphics;
import java.awt.Graphics2D;

import harmony.gui.Types;
import harmony.gui.graph.Space;
import harmony.gui.graph.elements.Node;
import harmony.math.Vector2D;
import harmony.processcore.data.DataPattern;
import harmony.processcore.data.DataType;
import harmony.processcore.data.DataTypes;
import harmony.processcore.process.HrmProcess;
import harmony.processcore.process.units.utils.InputOutputBuffer;

public class Display extends Node {

	public Display(Space space) {
		super(space, new HrmProcess("display",
				new InputOutputBuffer(new DataPattern(new DataType[] { DataTypes.Double }, new String[] { "in" }))));
	}
	
	private Double getValue() {
		assert getProcess().getComputeUnit() instanceof InputOutputBuffer : "unexpected compute unit type";
		getProcess().getValues();
		InputOutputBuffer buffer = (InputOutputBuffer) getProcess().getComputeUnit();
		return (Double) buffer.getValue(0);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g.create();

		Vector2D topLeft = getPos().subtract(getSize().multiply(0.5));
		
		g2d.drawString(Types.getDataString(getValue()), (float) topLeft.x + 0.8f, (float) topLeft.y + 0.8f);

		g2d.dispose();
	}
}
