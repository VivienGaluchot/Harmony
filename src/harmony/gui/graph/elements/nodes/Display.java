package harmony.gui.graph.elements.nodes;

import harmony.gui.graph.Space;
import harmony.gui.graph.elements.InPort;
import harmony.gui.graph.elements.Node;

public class Display extends Node {

	private InPort in1;
	private InPort in2;
	private InPort in3;

	public Display(Space space) {
		super(space, "Display");

		in1 = new InPort(this, Integer.class, "in1");
		in2 = new InPort(this, Float.class, "in2");
		in3 = new InPort(this, Double.class, "in3");

		addInPort(in1);
		addInPort(in2);
		addInPort(in3);
		
		// TODO show values
	}

}
