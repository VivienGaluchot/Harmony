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
		String name = Dialog.StringDialog(null, "Enter constant name", constantName);
		if (name == null)
			return;
		constantName = name;
		setName(constantName);

		Double d = Dialog.DoubleDialog(null, "Enter a value", value.toString());
		if (d == null)
			return;
		setValue(d);
	}
}
