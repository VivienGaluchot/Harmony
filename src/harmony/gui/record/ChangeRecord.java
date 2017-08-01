package harmony.gui.record;

public abstract class ChangeRecord {

	private Recordable father;

	public ChangeRecord(Recordable father) {
		if (father == null)
			throw new IllegalArgumentException();

		this.father = father;
	}

	public Recordable getFather() {
		return father;
	}

	public abstract void undoChange();

	public abstract void redoChange();

}
