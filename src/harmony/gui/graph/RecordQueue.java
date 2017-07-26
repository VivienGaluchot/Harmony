package harmony.gui.graph;

import java.util.ArrayList;
import java.util.Set;

public class RecordQueue {

	private ArrayList<Set<ChangeRecord>> recordArray;
	private int nStep;
	private int maxSize;

	public RecordQueue() {
		recordArray = new ArrayList<>();
		nStep = 0;
		maxSize = 100;
	}

	public void addRecords(Set<ChangeRecord> changes) {
		recordArray.add(nStep, changes);
		nStep++;

		// remove too long queue
		while (recordArray.size() > maxSize) {
			recordArray.remove(0);
			nStep--;
		}

		// remove head
		while (recordArray.size() > nStep)
			recordArray.remove(nStep);
	}

	public Set<ChangeRecord> getUndoRecords() {
		if (nStep <= 0)
			return null;
		nStep--;
		Set<ChangeRecord> rec = recordArray.get(nStep);
		return rec;
	}

	public Set<ChangeRecord> getRedoRecords() {
		if (nStep >= recordArray.size())
			return null;
		Set<ChangeRecord> rec = recordArray.get(nStep);
		nStep++;
		return rec;
	}

	public ChangeRecord getPreviousRecord(Recordable father) {
		for (int i = nStep - 1; i >= 0; i--) {
			Set<ChangeRecord> set = recordArray.get(i);
			for (ChangeRecord cr : set)
				if (cr.getFather() == father)
					return cr;
		}
		return null;
	}

}
