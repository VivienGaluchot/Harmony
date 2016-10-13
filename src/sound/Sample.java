package sound;

public class Sample {
	// Audio parameters
	public final static float sampleRate = 44100.0F;
	// Allowable 8000,11025,16000,22050,44100
	public final static int sampleSizeInBits = 16;
	// Allowable 8,16
	public final static int channels = 2;
	// Allowable 1,2
	public final static boolean signed = true;
	// Allowable true,false
	public final static boolean bigEndian = true;

	public final static float samplePeriod = 1 / sampleRate;
	public final static int byteLength = (int) sampleSizeInBits / 8 * channels;
	public final static int bytesPerSec = (int) (sampleRate * byteLength);
	public final static int samplePerSec = (int) (sampleRate * channels);

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

	public void add(Sample s) {
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
