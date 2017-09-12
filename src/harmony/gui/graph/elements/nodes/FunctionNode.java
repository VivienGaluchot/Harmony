package harmony.gui.graph.elements.nodes;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import harmony.data.DataDescriptor;
import harmony.data.DataDescriptorModel;
import harmony.data.DataGenerator;
import harmony.gui.DrawPanel;
import harmony.gui.SubFrame;
import harmony.gui.graph.Space;
import harmony.gui.graph.elements.InPort;
import harmony.gui.graph.elements.Node;
import harmony.gui.graph.elements.OutPort;

public class FunctionNode extends Node {

	private Space insideSpace;
	private SubFrame editFrame;

	public FunctionNode(Space hostSpace, String name) {
		super(hostSpace, name);
		
		List<DataGenerator> inputs = new ArrayList<>();
		inputs.add(new InPort(this, Double.class, "I1"));
		inputs.add(new InPort(this, Double.class, "I2"));

		List<DataDescriptor> outputs = new ArrayList<>();
		outputs.add(new DataDescriptorModel(Double.class, "O1"));
		outputs.add(new DataDescriptorModel(Double.class, "O2"));

		insideSpace = new Space(name, inputs, outputs);
		
		OutPort modelOutPort = new OutPort(this, FunctionNode.class, "model"){
			@Override
			public Set<DataGenerator> getDataProcessDependencies() {
				return null;
			}

			@Override
			public Object getData() {
				return FunctionNode.this;
			}
		};
		addOutPort(modelOutPort);
	}

	@Override
	public void showOpt(Component parent) {
		if (editFrame == null) {
			DrawPanel panel = new DrawPanel(insideSpace);
			editFrame = new SubFrame(panel);
		} else {
			editFrame.setVisible(true);
			editFrame.requestFocusInWindow();
		}
	}

}
