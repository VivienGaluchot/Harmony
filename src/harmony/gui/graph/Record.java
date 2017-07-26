package harmony.gui.graph;

public abstract class Record {

	Object father;

	public Record(Object father) {
		if (father == null)
			throw new IllegalArgumentException();

		this.father = father;
	}
	
	public abstract boolean isFatherUpdated();
	public abstract void updateFather();

}
