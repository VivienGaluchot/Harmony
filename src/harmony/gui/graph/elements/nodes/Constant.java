package harmony.gui.graph.elements.nodes;

import java.awt.Component;
import java.awt.Graphics;
import java.util.Set;

import harmony.gui.Dialog;
import harmony.gui.graph.Space;
import harmony.gui.graph.elements.InPort;
import harmony.gui.graph.elements.Node;
import harmony.gui.graph.elements.OutPort;

public class Constant extends Node {
	
	private Double value = 0.0;

	public Constant(Space space) {
		super(space, "Constant");
		
		OutPort out = new OutPort(this, Double.class, "value") {
			@Override
			public Set<InPort> getDependencies() {
				return null;
			}

			@Override
			public Object getValue() {
				return value;
			}
			
			@Override
			public void paint(Graphics g) {
				Object v = this.getValue();
				if(v != null)
					this.name = "value : " + v.toString();
				else
					this.name = "value";
				super.paint(g);
			}
		};
		
		addOutPort(out);
	}
	
	public void setValue(Double x){
		value = x;
	}
	
	@Override
	public void showOpt(Component parent) {
		Double d = Dialog.DoubleDialog(null, "Enter a value", value.toString());
		if (d != null)
			setValue(d);
	}
}
