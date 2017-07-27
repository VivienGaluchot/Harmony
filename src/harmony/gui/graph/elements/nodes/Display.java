package harmony.gui.graph.elements.nodes;

import harmony.gui.Types;
import harmony.gui.graph.Space;
import harmony.gui.graph.elements.InPort;
import harmony.gui.graph.elements.Node;

public class Display extends Node {

	private InPort<Integer> in1;
	private InPort<Float> in2;
	private InPort<Double> in3;

	public Display(Space space) {
		super(space, "Display");

		in1 = new InPort<Integer>(this, Types.DataType.INTEGER, "in1");
		in2 = new InPort<Float>(this, Types.DataType.FLOAT, "in2");
		in3 = new InPort<Double>(this, Types.DataType.DOUBLE, "in3");

		addInPort(in1);
		addInPort(in2);
		addInPort(in3);
	}

	@Override
	public void execute() {
		in1.name = "in1 : " + in1.getValue();
		in2.name = "in2 : " + in2.getValue();
		in3.name = "in3 : " + in3.getValue();
	}

}
