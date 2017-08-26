//    Harmony : procedural sound waves generator
//    Copyright (C) 2017  Vivien Galuchot
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, version 3 of the License.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

package harmony.gui.graph.elements.nodes;

import java.awt.Component;
import java.util.ArrayList;
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

	public ProcessNode(Space space, String name, ProcessScheme scheme) {
		this(space, name);
		ArrayList<ProcessScheme> schemes = new ArrayList<>();
		schemes.add(scheme);
		initPorts(schemes);
	}

	public ProcessNode(Space space, String name, List<ProcessScheme> schemes) {
		this(space, name);
		initPorts(schemes);
	}

	public ProcessNode(Space space, String name) {
		super(space, name);
	}

	protected void initPorts(List<ProcessScheme> schemes) {
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
