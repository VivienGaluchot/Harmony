package harmony.gui.graph.elements.nodes;

import harmony.gui.graph.Space;
import harmony.gui.graph.elements.InPort;
import harmony.gui.graph.elements.Node;
import harmony.gui.graph.elements.OutPort;

public class Default extends Node {

	private InPort in1;
	private InPort in2;
	private InPort in3;
	private OutPort out1;
	private OutPort out2;

	public Default(Space space) {
		super(space, "Default");

		in1 = new InPort(this, Integer.class, "in1");
		in2 = new InPort(this, Float.class, "in2");
		in3 = new InPort(this, Double.class, "in3");
		out1 = new OutPort(this, Integer.class, "out1 = in1") {
			@Override
			public Object getValue() {
				return in1.getValue();
			}
		};
		out2 = new OutPort(this, Float.class, "out2 = 2*in2") {
			@Override
			public Object getValue() {
				if (in2.getValue() != null)
					return (Float) in2.getValue() * 2;
				else
					return new Float(1);
			}

		};

		addInPort(in1);
		addInPort(in2);
		addInPort(in3);
		addOutPort(out1);
		addOutPort(out2);
	}
}
