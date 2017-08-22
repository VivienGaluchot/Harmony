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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class RecordQueue {

	private HashMap<Recordable, StateRecord> trackHead;

	private ArrayList<Set<ChangeRecord>> changesArray;

	private int nStep;
	private int maxSize;

	public RecordQueue() {
		trackHead = new HashMap<>();
		changesArray = new ArrayList<>();
		nStep = 0;
		maxSize = 100;
	}

	public void addTrackedObject(Recordable rec) {
		trackHead.put(rec, rec.getCurrentState());
	}

	public void removeTrackedObject(Recordable rec) {
		// TODO remove trackedObject
		// the given object won't change now
		// can be removed from track when not anymore referenced in changes
	}

	public void undo() {
		Set<ChangeRecord> records = getUndoRecords();
		if (records != null) {
			for (ChangeRecord record : records) {
				record.undoChange();
				trackHead.put(record.getFather(), record.getFather().getCurrentState());
			}
		}
	}

	public void redo() {
		Set<ChangeRecord> records = getRedoRecords();
		if (records != null) {
			for (ChangeRecord record : records) {
				record.redoChange();
				trackHead.put(record.getFather(), record.getFather().getCurrentState());
			}
		}
	}

	public void trackDiffs() {
		Set<ChangeRecord> changes = new HashSet<>();
		for (Recordable rec : trackHead.keySet()) {
			StateRecord currentState = rec.getCurrentState();
			ChangeRecord currentDiffs = trackHead.get(rec).getDiffs(currentState);
			if (currentDiffs != null) {
				trackHead.put(rec, currentState);
				changes.add(currentDiffs);
			}
		}
		if (changes.size() > 0)
			addRecords(changes);
	}

	// Queue

	private void addRecords(Set<ChangeRecord> changes) {
		if (changes.size() == 0)
			return;

		changesArray.add(nStep, changes);
		nStep++;

		// remove too long queue
		while (changesArray.size() > maxSize) {
			changesArray.remove(0);
			nStep--;
		}

		// remove head
		while (changesArray.size() > nStep)
			changesArray.remove(nStep);
	}

	private Set<ChangeRecord> getUndoRecords() {
		if (nStep <= 0)
			return null;
		nStep--;
		Set<ChangeRecord> rec = changesArray.get(nStep);
		return rec;
	}

	private Set<ChangeRecord> getRedoRecords() {
		if (nStep >= changesArray.size())
			return null;
		Set<ChangeRecord> rec = changesArray.get(nStep);
		nStep++;
		return rec;
	}
}
