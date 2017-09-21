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
import java.awt.Graphics;
import java.util.Set;

import harmony.data.DataGenerator;
import harmony.gui.Dialog;
import harmony.gui.graph.Space;
import harmony.gui.graph.elements.Node;
import harmony.gui.graph.elements.OutPort;

public class Constant extends Node {

	private Double value = 0.0;
	private String constantName;

	public Constant(Space space) {
		super(space, "Constant");

		this.constantName = getName();

		OutPort out = new OutPort(this, Double.class, "value") {
			@Override
			public Set<DataGenerator> getDataProcessDependencies() {
				return null;
			}

			@Override
			public Object getData() {
				return value;
			}

			@Override
			public void paint(Graphics g) {
				Object v = this.getData();
				if (v != null)
					this.name = "value : " + v.toString();
				else
					this.name = "value";
				super.paint(g);
			}
		};

		addOutPort(out);
	}

	public void setValue(Double x) {
		value = x;
	}

	@Override
	public void showOpt(Component parent) {
		String name = Dialog.stringDialog(null, "Enter constant name", constantName);
		if (name == null)
			return;
		constantName = name;
		setName(constantName);

		Double d = Dialog.doubleDialog(null, "Enter a value", value.toString());
		if (d == null)
			return;
		setValue(d);
	}
}
