package sound.generation;

public interface WaveGenerator {

	/**
	 * Reset the sound module
	 */
	public void reset();

	/**
	 * Sound generating function
	 * 
	 * @return the next sound's sample
	 */
	public double next();

	/**
	 * @return true if the generator can give more samples, false if the
	 *         generator can't give more samples
	 */
	public boolean hasNext();

}