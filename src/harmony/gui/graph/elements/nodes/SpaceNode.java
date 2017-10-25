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

import java.util.ArrayList;
import java.util.List;

import harmony.dataprocess.implem.DataDescriptorModel;
import harmony.dataprocess.model.DataDescriptor;
import harmony.dataprocess.model.DataGenerator;
import harmony.gui.SpaceEditFrame;
import harmony.gui.graph.Space;
import harmony.gui.graph.elements.InPort;
import harmony.gui.graph.elements.Node;
import harmony.gui.graph.elements.OutPort;

public class SpaceNode extends Node {

	private Space insideSpace;
	private SpaceEditFrame editFrame;

	public SpaceNode(Space hostSpace, String name) {
		super(hostSpace, name);

		InPort inPort1 = new InPort(this, Double.class, "I1");
		InPort inPort2 = new InPort(this, Double.class, "I2");
		addInPort(inPort1);
		addInPort(inPort2);
		List<DataGenerator> inputs = new ArrayList<>();
		inputs.add(inPort1);
		inputs.add(inPort2);

		List<DataDescriptor> outputs = new ArrayList<>();
		outputs.add(new DataDescriptorModel(Double.class, "O1"));
		outputs.add(new DataDescriptorModel(Double.class, "O2"));

		insideSpace = new Space(name, inputs, outputs);

		List<OutPort> outPorts = insideSpace.getOutputNode().createAssociedOutPortList(this);
		for (OutPort outPort : outPorts) {
			addOutPort(outPort);
		}
	}

	@Override
	public void showOpt() {
		if (editFrame == null) {
			editFrame = new SpaceEditFrame(insideSpace);
		} else {
			editFrame.setVisible(true);
			editFrame.requestFocusInWindow();
		}
	}

}
