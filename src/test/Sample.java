package test;

public class Sample {
	// Audio parameters
	final static float sampleRate = 44100.0F;
	// Allowable 8000,11025,16000,22050,44100
	final static int sampleSizeInBits = 16;
	// Allowable 8,16
	final static int channels = 2;
	// Allowable 1,2
	final static boolean signed = true;
	// Allowable true,false
	final static boolean bigEndian = true;

	final static float samplePeriod = 1 / sampleRate;
	final static int byteLength = (int) sampleSizeInBits / 8 * channels;
	final static int bytesPerSec = (int) (sampleRate * byteLength);
	final static int samplePerSec = (int) (sampleRate * channels);

	public short left;
	public short right;
	
	public Sample() {
		this.left = 0;
		this.right = 0;
	}
	
	public Sample(short v) {
		this.left = v;
		this.right = v;
	}

	public Sample(short left, short right) {
		this.left = left;
		this.right = right;
	}
	
	public void add(Sample s){
		left += s.left;
		right += s.right;
	}

	public byte[] toBytes() {
		byte[] bytes = new byte[byteLength];
		bytes[0] = (byte) (left >> 8);
		bytes[1] = (byte) (left);
		bytes[2] = (byte) (right >> 8);
		bytes[3] = (byte) (right);
		return bytes;
	}
}
