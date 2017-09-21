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

import java.util.Iterator;
import java.util.List;

import harmony.gui.graph.Space;
import harmony.gui.graph.elements.Node;
import harmony.gui.persist.Persistor;

public class SpacePersistor extends Persistor<Space> {
	private static final long serialVersionUID = 1L;

	String name;
	Persistor<Node>[] nodePersistorArray;

	public SpacePersistor(Space source) {
		super(source);

		name = source.getName();

		int i = 0;
		List<Node> nodes = source.getNodes();
		Iterator<Node> it = nodes.iterator();
		nodePersistorArray = new NodePersistor[nodes.size()];
		while (it.hasNext() && i < nodePersistorArray.length)
			nodePersistorArray[i++] = it.next().getCurrentPersistRecord();
	}

	@Override
	public Space recreate() {
		Space s = new Space(name, null, null);
		return s;
	}

	@Override
	public void update(Space source) {
		if (source.getNodes().size() > 0)
			throw new IllegalArgumentException("Non empty space");
		for (int i = 0; i < nodePersistorArray.length; i++) {
			if (nodePersistorArray[i] != null)
				source.addNode(nodePersistorArray[i].recreate());
			else
				System.out.println("null persistor loaded");
		}
	}

}
