package harmony.gui.graph.elements.nodes;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import harmony.data.DataDescriptor;
import harmony.gui.graph.Space;
import harmony.gui.graph.elements.InPort;
import harmony.gui.graph.elements.Node;

public class SpaceOutputNode extends Node {
	
	private HashMap<DataDescriptor, InPort> portMap;

	public SpaceOutputNode(Space space, List<DataDescriptor> outputs) {
		super(space, "Output");
		
		portMap = new HashMap<>();

		this.setBackgroundColor(new Color(240, 240, 240));

		for (DataDescriptor des : outputs) {
			InPort in = new InPort(this, des.getDataClass(), des.getDataName());
			portMap.put(des, in);
			addInPort(in);
		}
	}
	
	public Object getData(DataDescriptor descriptor) {
		InPort port = portMap.get(descriptor);
		if(port == null)
			throw new IllegalArgumentException();
		return port.getData();
	}

	@Override
	public void showOpt(Component parent) {
		JOptionPane.showMessageDialog(parent, "Result must be put in this OutputNode");
	}
}
