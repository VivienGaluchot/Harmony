package sound.waveMix;

import sound.Generator;
import sound.Sample;

public class VolumeAdjuster implements Generator {

	private Generator source;
	private float volumeAdjust;

	public VolumeAdjuster(Generator source, float volumeAdjust) {
		this.source = source;
		this.volumeAdjust = volumeAdjust;
	}

	@Override
	public Sample next() {
		Sample s = source.next();
		s.left *= volumeAdjust;
		s.right *= volumeAdjust;
		return s;
	}

	@Override
	public boolean hasNext() {
		return source.hasNext();
	}

	public void setVolumeAdjust(float volumeAdjust) {
		this.volumeAdjust = volumeAdjust;
	}

	public float getVolumeAdjust() {
		return volumeAdjust;
	}
}
