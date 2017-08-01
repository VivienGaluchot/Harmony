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
