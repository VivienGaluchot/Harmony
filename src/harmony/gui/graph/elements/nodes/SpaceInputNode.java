package harmony.gui.graph.elements.nodes;

import java.util.List;
import java.util.Set;

import harmony.data.DataGenerator;
import harmony.data.DataProcessor;
import harmony.gui.graph.Space;
import harmony.gui.graph.elements.Node;
import harmony.gui.graph.elements.OutPort;

public class SpaceInputNode extends Node {

	public SpaceInputNode(Space space, List<DataGenerator> inputs) {
		super(space, "Input");

		for (DataGenerator gen : inputs) {
			OutPort out = new OutPort(this, gen.getDataClass(), gen.getDataName()) {
				@Override
				public Set<DataProcessor> getDataProcessDependencies() {
					return null;
				}

				@Override
				public Object getData() {
					return gen.getData();
				}
			};
			addOutPort(out);
		}
	}

}
