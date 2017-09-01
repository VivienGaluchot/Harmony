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

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import harmony.data.DataDescriptor;
import harmony.data.DataGenerator;
import harmony.gui.graph.Space;
import harmony.gui.graph.elements.InPort;
import harmony.gui.graph.elements.Node;
import harmony.gui.graph.elements.OutPort;

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

	public List<OutPort> createOutPortList(Node father) {
		List<OutPort> outPorts = new ArrayList<>();
		for (DataDescriptor des : portMap.keySet()) {
			InPort inPort = portMap.get(des);
			OutPort outPort = new OutPort(father, des.getDataClass(), des.getDataName()) {
				@Override
				public Set<DataGenerator> getDataProcessDependencies() {
					return inPort.getDataProcessDependencies();
				}

				@Override
				public Object getData() {
					return inPort.getData();
				}
			};
			outPorts.add(outPort);
		}
		return outPorts;
	}

	public Object getData(DataDescriptor descriptor) {
		InPort port = portMap.get(descriptor);
		if (port == null)
			throw new IllegalArgumentException();
		return port.getData();
	}

	@Override
	public void showOpt(Component parent) {
		JOptionPane.showMessageDialog(parent, "Result must be put in this OutputNode");
	}
}
