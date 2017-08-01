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

package harmony.gui.record;

public abstract class StateRecord {
	
	private Recordable father;

	public StateRecord(Recordable father) {
		if (father == null)
			throw new IllegalArgumentException();

		this.father = father;
	}

	public Recordable getFather() {
		return father;
	}

	/**
	 * Return a change record containing differences between ref and this
	 * 
	 * @param ref
	 *            : reference state, the father should be in this state after
	 *            applying the change
	 * @return differences between ref and this, null if there are the same
	 *         state
	 */
	public abstract ChangeRecord getDiffs(StateRecord ref);

}
