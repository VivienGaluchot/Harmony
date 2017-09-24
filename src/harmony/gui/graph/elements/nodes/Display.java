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

import java.awt.Graphics;

import javax.swing.JOptionPane;

import harmony.gui.graph.Space;
import harmony.gui.graph.elements.InPort;
import harmony.gui.graph.elements.Node;

public class Display extends Node {

	private InPort in1;
	private InPort in2;
	private InPort in3;

	public Display(Space space) {
		super(space, "Display");

		in1 = new InPort(this, Integer.class, "in1") {
			@Override
			public void paint(Graphics g) {
				Object v = this.getData();
				if (v != null)
					this.name = v.toString();
				else
					this.name = "-";
				super.paint(g);
			}
		};
		in2 = new InPort(this, Float.class, "in2") {
			@Override
			public void paint(Graphics g) {
				Object v = this.getData();
				if (v != null)
					this.name = v.toString();
				else
					this.name = "-";
				super.paint(g);
			}
		};
		in3 = new InPort(this, Double.class, "in3") {
			@Override
			public void paint(Graphics g) {
				Object v = this.getData();
				if (v != null)
					this.name = v.toString();
				else
					this.name = "-";
				super.paint(g);
			}
		};

		addInPort(in1);
		addInPort(in2);
		addInPort(in3);
	}

	@Override
	public void showOpt() {
		JOptionPane.showMessageDialog(getFather(), "Display Node");
	}

}
