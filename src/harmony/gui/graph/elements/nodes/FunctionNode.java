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
import java.util.Set;

import harmony.dataprocess.implem.DataDescriptorModel;
import harmony.dataprocess.model.DataDescriptor;
import harmony.dataprocess.model.DataGenerator;
import harmony.gui.SpaceEditFrame;
import harmony.gui.graph.Space;
import harmony.gui.graph.elements.InPort;
import harmony.gui.graph.elements.Node;
import harmony.gui.graph.elements.OutPort;

public class FunctionNode extends Node {

	private Space insideSpace;
	private SpaceEditFrame editFrame;

	public FunctionNode(Space hostSpace, String name) {
		super(hostSpace, name);

		List<DataGenerator> inputs = new ArrayList<>();
		inputs.add(new InPort(this, Double.class, "I1"));
		inputs.add(new InPort(this, Double.class, "I2"));

		List<DataDescriptor> outputs = new ArrayList<>();
		outputs.add(new DataDescriptorModel(Double.class, "O1"));
		outputs.add(new DataDescriptorModel(Double.class, "O2"));

		insideSpace = new Space(name, inputs, outputs);

		OutPort modelOutPort = new OutPort(this, FunctionNode.class, "model") {
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
	public void showOpt() {
		if (editFrame == null) {
			editFrame = new SpaceEditFrame(insideSpace);
		} else {
			editFrame.setVisible(true);
			editFrame.requestFocusInWindow();
		}
	}

}
