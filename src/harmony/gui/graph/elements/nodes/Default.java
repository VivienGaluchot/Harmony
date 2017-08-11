package harmony.gui.graph.elements.nodes;

import java.util.HashSet;
import java.util.Set;

import harmony.data.DataProcessor;
import harmony.gui.graph.Space;
import harmony.gui.graph.elements.InPort;
import harmony.gui.graph.elements.Node;
import harmony.gui.graph.elements.OutPort;

public class Default extends Node {

	private InPort in1;
	private InPort in2;
	private OutPort out1;
	private OutPort out2;

	public Default(Space space) {
		super(space, "Default");

		in1 = new InPort(this, Integer.class, "in1");
		in2 = new InPort(this, Double.class, "in2");
		
		out1 = new OutPort(this, Integer.class, "out1 = in1") {
			@Override
			public Object getData() {
				return in1.getData();
			}

			@Override
			public Set<DataProcessor> getDataProcessDependencies() {
				Set<DataProcessor> set = new HashSet<>();
				set.add(in1);
				return set;
			}
		};
		out2 = new OutPort(this, Double.class, "out2 = 2*in2") {
			@Override
			public Object getData() {
				if (in2.getData() != null)
					return (Double) in2.getData() * 2;
				else
					return new Double(1);
			}

			@Override
			public Set<DataProcessor> getDataProcessDependencies() {
				Set<DataProcessor> set = new HashSet<>();
				set.add(in2);
				return set;
			}
		};

		addInPort(in1);
		addInPort(in2);
		addOutPort(out1);
		addOutPort(out2);
	}
}
