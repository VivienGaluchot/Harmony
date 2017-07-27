package harmony.gui.graph.elements.nodes;

import harmony.gui.Types;
import harmony.gui.graph.Space;
import harmony.gui.graph.elements.InPort;
import harmony.gui.graph.elements.Node;
import harmony.gui.graph.elements.OutPort;

public class Default extends Node {

	private InPort<Integer> in1;
	private InPort<Float> in2;
	private InPort<Double> in3;
	private OutPort<Integer> out1;
	private OutPort<Float> out2;

	public Default(Space space) {
		super(space, "Default");

		in1 = new InPort<Integer>(this, Types.DataType.INTEGER, "in1");
		in2 = new InPort<Float>(this, Types.DataType.FLOAT, "in2");
		in3 = new InPort<Double>(this, Types.DataType.DOUBLE, "in3");
		out1 = new OutPort<Integer>(this, Types.DataType.INTEGER, "out1 = in1");
		out2 = new OutPort<Float>(this, Types.DataType.FLOAT, "out2 = 2*in2");

		addInPort(in1);
		addInPort(in2);
		addInPort(in3);
		addOutPort(out1);
		addOutPort(out2);
	}

	@Override
	public void execute() {
		if(in1.getValue() != null)
			out1.setValue(in1.getValue());
		else
			out1.setValue(null);
		
		if(in2.getValue() != null)
			out2.setValue(in2.getValue() * 2);
		else
			out2.setValue(null);
	}

}
