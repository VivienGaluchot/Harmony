//    Harmony : procedural sound waves generator
//    Copyright (C) 2017  Vivien Galuchot
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, version 3 of the License.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

package harmony.sound;

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

	public final static double samplePeriod = 1 / sampleRate;
	public final static int byteLength = sampleSizeInBits / 8 * channels;
	public final static int bytesPerSec = (int) (sampleRate * byteLength);
	public final static int samplePerSec = (int) (sampleRate * channels);

	public short left;
	public short right;

	public Sample() {
		this.left = 0;
		this.right = 0;
	}

	/**
	 * @param left,
	 *            left audio value, between -1 & 1
	 * @param right,
	 *            right audio value, between -1 & 1
	 */
	public Sample(double left, double right) {
		// Saturation
		if (left > 1)
			left = 1;
		else if (left < -1)
			left = -1;
		if (right > 1)
			right = 1;
		else if (right < -1)
			right = -1;

		this.left = (short) (left * Short.MAX_VALUE);
		this.right = (short) (right * Short.MAX_VALUE);
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
