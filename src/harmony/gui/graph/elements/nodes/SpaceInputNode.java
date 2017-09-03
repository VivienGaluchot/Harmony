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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import harmony.data.DataGenerator;
import harmony.data.DataProcessor;
import harmony.gui.graph.Space;
import harmony.gui.graph.elements.Node;
import harmony.gui.graph.elements.OutPort;

public class SpaceInputNode extends Node {

	public SpaceInputNode(Space space, List<DataGenerator> inputs) {
		super(space, "Input");

		setBackgroundColor(new Color(240, 240, 240));

		for (DataGenerator gen : inputs) {
			OutPort out = new OutPort(this, gen.getDataClass(), gen.getDataName()) {
				@Override
				public Set<DataGenerator> getDataProcessDependencies() {
					Set<DataGenerator> dependencies = new HashSet<>();
					if (gen instanceof DataProcessor)
						dependencies.add(gen);
					return dependencies;
				}

				@Override
				public Object getData() {
					return gen.getData();
				}
			};
			addOutPort(out);
		}
	}

	@Override
	public void showOpt(Component parent) {
		JOptionPane.showMessageDialog(parent, "Data to process flows out of this InputNode");
	}

}
