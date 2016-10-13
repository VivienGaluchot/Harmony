package sound;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import sound.waveGen.SinWave;
import sound.waveGen.SquareWave;
import sound.waveMix.DurationAdjuster;
import sound.waveMix.VolumeAdjuster;

public class MainTest {
	public static void main(String[] args) throws Exception {
		final JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.add(new JLabel("-- Harmony --"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(200,200));
		frame.setVisible(true);

		Concentrator c = new Concentrator();
		c.listen();

		c.addGenerator(new VolumeAdjuster(new SinWave(50),0.1f));

		while (true) {
			Thread.sleep(300);
			c.addGenerator(new DurationAdjuster(new VolumeAdjuster(new SinWave(100),0.2f),0.1f));
			Thread.sleep(100);
			c.addGenerator(new DurationAdjuster(new VolumeAdjuster(new SquareWave(300),0.05f),0.05f));
		}
	}
}