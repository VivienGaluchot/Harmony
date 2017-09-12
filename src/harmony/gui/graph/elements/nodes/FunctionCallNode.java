package harmony.gui.graph.elements.nodes;

import java.awt.Component;

import harmony.gui.graph.Space;
import harmony.gui.graph.elements.InPort;
import harmony.gui.graph.elements.Node;

public class FunctionCallNode extends Node {

	public FunctionCallNode(Space space) {
		super(space, "FunctionCall");
		
		InPort modelPort = new InPort(this, FunctionNode.class, "model");
		addInPort(modelPort);
	}

	@Override
	public void showOpt(Component parent) {
		// TODO Auto-generated method stub

	}

}
