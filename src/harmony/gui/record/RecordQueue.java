package harmony.gui.record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
		Iterator<Recordable> it = trackHead.keySet().iterator();
		while (it.hasNext()) {
			Recordable rec = it.next();
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
