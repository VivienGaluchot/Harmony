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
import java.util.List;

import harmony.data.DataDescriptor;
import harmony.data.DataDescriptorModel;
import harmony.data.DataGenerator;
import harmony.gui.DrawPanel;
import harmony.gui.SubFrame;
import harmony.gui.graph.Space;
import harmony.gui.graph.elements.InPort;
import harmony.gui.graph.elements.Node;
import harmony.gui.graph.elements.OutPort;

public class SpaceNode extends Node {

	private Space inSpace;

	public SpaceNode(Space space, String name) {
		super(space, name);
		inSpace = new Space();

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
		SpaceOutputNode outputNode = new SpaceOutputNode(inSpace, outputs);
		List<OutPort> outPorts = outputNode.createOutPortList(this);
		for (OutPort outPort : outPorts) {
			addOutPort(outPort);
		}

		inSpace.init(inputs, outputNode);
		
		// TODO fix detection of infinite loop due to recurtion
	}

	@Override
	public void showOpt(Component parent) {
		DrawPanel panel = new DrawPanel(inSpace);
		new SubFrame(panel);
	}

}
