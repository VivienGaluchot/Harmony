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

package harmony.gui.persist.persistors;

import harmony.gui.graph.elements.Node;
import harmony.gui.graph.elements.nodes.Constant;
import harmony.gui.persist.Persistor;
import harmony.math.Vector2D;

public class NodePersistor extends Persistor<Node> {
	private static final long serialVersionUID = 1L;
	
	double posX;
	double posY;
	String name;

	public NodePersistor(Node source) {
		super(source);
		posX = source.pos.x;
		posY = source.pos.y;
		name = source.getName();
	}

	@Override
	public Node recreate() {
		Node n = new Constant(null);
		update(n);
		return n;
	}

	@Override
	public void update(Node source) {
		source.pos = new Vector2D(posX, posY);
	}

}
