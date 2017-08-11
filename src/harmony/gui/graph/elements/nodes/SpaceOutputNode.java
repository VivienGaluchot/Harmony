package harmony.gui.graph.elements.nodes;

import java.util.List;

import harmony.data.DataDescriptor;
import harmony.gui.graph.Space;
import harmony.gui.graph.elements.InPort;
import harmony.gui.graph.elements.Node;

public class SpaceOutputNode extends Node {

	public SpaceOutputNode(Space space, List<DataDescriptor> outputs) {
		super(space, "Output");

		for (DataDescriptor des : outputs) {
			InPort in = new InPort(this, des.getDataClass(), des.getDataName());
			addInPort(in);
		}
	}

}
