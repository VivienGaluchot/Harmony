package harmony.gui.graph.elements.nodes;

import java.awt.Component;
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
	public void showOpt(Component parent) {
		JOptionPane.showMessageDialog(parent, "Display Node");
	}

}
