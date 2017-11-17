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

package harmony.gui.graph.elements;

import harmony.gui.Dialog;
import harmony.processcore.data.DataTypes;
import harmony.processcore.process.HrmProcess;
import harmony.processcore.process.units.maths.Constant;

public class ProcessUpdater {
	private Node node;
	private HrmProcess process;

	public ProcessUpdater(Node node, HrmProcess process) {
		this.node = node;
		this.process = process;
	}

	public void update() {
		if (process == null) {
			Dialog.displayMessage(node.getFatherComponent(), "Empty node");
		} else if (process.getComputeUnit() instanceof Constant) {
			Double current = (Double) process.getValue(0);
			Double updated = Dialog.doubleDialog(node.getFatherComponent(), "Update constant value",
					current.toString());
			if (updated != null) {
				process.replaceComputeUnit(new Constant(DataTypes.Double, updated));
			}
		}
	}
}
