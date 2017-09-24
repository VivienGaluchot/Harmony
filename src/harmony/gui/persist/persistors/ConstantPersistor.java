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

public class ConstantPersistor extends NodePersistor {
	private static final long serialVersionUID = 1L;

	double value;

	public ConstantPersistor(Constant source) {
		super(source);
		value = source.getValue();
	}

	@Override
	public Node recreate() {
		Constant c = new Constant();
		update(c);
		return c;
	}

	@Override
	public void update(Node source) {
		if (!(source instanceof Constant))
			throw new IllegalArgumentException("Wrong source object class");
		super.update(source);
		Constant c = (Constant) source;
		c.setValue(value);
	}

}