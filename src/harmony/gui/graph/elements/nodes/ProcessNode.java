package harmony.gui.graph.elements.nodes;

import java.awt.Component;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import harmony.data.DataDescriptor;
import harmony.data.DataGenerator;
import harmony.data.ProcessScheme;
import harmony.gui.graph.Space;
import harmony.gui.graph.elements.InPort;
import harmony.gui.graph.elements.Node;
import harmony.gui.graph.elements.OutPort;

public class ProcessNode extends Node {

	private Map<DataDescriptor, DataGenerator> inPortsMap;

	public ProcessNode(Space space, String name, List<ProcessScheme> schemes) {
		super(space, name);

		inPortsMap = new HashMap<>();

		// Compute all dependencies
		Set<DataDescriptor> inDescriptors = new HashSet<>();
		for (ProcessScheme pr : schemes) {
			Set<DataDescriptor> dep = pr.getDependencies();
			if (dep != null)
				inDescriptors.addAll(dep);
		}

		// Create InPorts
		for (DataDescriptor des : inDescriptors) {
			InPort in = new InPort(this, des.getDataClass(), des.getDataName());
			inPortsMap.put(des, in);
			addInPort(in);
		}

		for (ProcessScheme pr : schemes) {
			OutPort out = new OutPort(this, pr.getDataClass(), pr.getDataName()) {
				private final Set<DataDescriptor> descriptors = pr.getDependencies();

				@Override
				public Set<DataGenerator> getDataProcessDependencies() {
					// TODO optimise, compute this only at construction
					Set<DataGenerator> processDependencies = new HashSet<>();
					if (descriptors != null) {
						for (DataDescriptor des : descriptors) {
							DataGenerator port = inPortsMap.get(des);
							processDependencies.add(port);
						}
					}
					return processDependencies;
				}

				@Override
				public Object getData() {
					return pr.process(inPortsMap);
				}
			};
			addOutPort(out);
		}
	}

	@Override
	public void showOpt(Component parent) {
		JOptionPane.showMessageDialog(parent, "Generic Node");
	}

}
