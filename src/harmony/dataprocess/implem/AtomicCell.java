package harmony.dataprocess.implem;


public class AtomicCell {
	
	// inputs
	private Class<?>[] inputsClassPattern;
	private AtomicCell[] inputs;
	
	// output
	private boolean valuated;
	private Object value;
	private Class<?> valueClass;
	
	public AtomicCell() {
		valuated = false;
	}
	
	public Object getValue() {
		if (!valuated)
			compute();
		return value;
	}
	
	private void compute() {
		
		valuated = true;
	}
}
