package harmony.sound.generation;

import harmony.sound.Sample;

public interface SampleGenerator {
	
	/**
	 * Reset the sound module
	 */
	public void reset();

	/**
	 * Sound generating function
	 * 
	 * @return the next sound's sample
	 */
	public Sample next();

	/**
	 * @return true if the generator can give more samples, false if the
	 *         generator can't give more samples
	 */
	public boolean hasNext();
	
}
