package harmony.gui.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RecordQueue {

	private ArrayList<Set<Record>> recordArray;
	private int currentStep;
	private int maxSize;

	public RecordQueue() {
		recordArray = new ArrayList<>();
		currentStep = 0;
		maxSize = 100;
	}

	public void addRecord(Record step) {
		HashSet<Record> set = new HashSet<>();
		addRecords(set);
	}

	public void addRecords(Set<Record> step) {
		recordArray.add(currentStep, step);
		currentStep++;

		// remove too long queue
		while (recordArray.size() > maxSize) {
			recordArray.remove(0);
			currentStep--;
		}

		// remove head
		while (recordArray.size() > currentStep)
			recordArray.remove(currentStep);

	}

	public Set<Record> getUndoRecords() {
		if (currentStep <= 0)
			return null;

		currentStep = currentStep - 1;
		return recordArray.get(currentStep);
	}

	public Set<Record> getRedoRecords() {
		if (currentStep >= recordArray.size())
			return null;

		currentStep = currentStep + 1;
		return recordArray.get(currentStep);
	}
	
	
	// UNDO - REDO
	
	public void undo() {
		Set<Record> records = getUndoRecords();
		if(records != null) {
			for(Record record : records) {
				record.updateFather();
			}
		}
	}
	
	public void redo() {
		Set<Record> records = getRedoRecords();
		if(records != null) {
			for(Record record : records) {
				record.updateFather();
			}
		}
	}
}
