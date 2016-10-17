package sound;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import sound.generation.mix.DurationAdjuster;
import sound.generation.mix.Multiplier;
import sound.generation.mix.Adder;
import sound.generation.wave.Sin;
import sound.generation.wave.Square;

public class MainTest {
	public static void main(String[] args) throws Exception {
		final JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.add(new JLabel("-- Harmony --"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(200, 200));
		frame.setVisible(true);

		Adder mainPlayer = new Adder();

		Concentrator c = new Concentrator(mainPlayer);
		c.listen();

		mainPlayer.add(new Multiplier(new Multiplier(0.3f, new Sin(2)), new Sin(50)));
		while (true) {
			Thread.sleep(300);
			mainPlayer.add(new DurationAdjuster(0.1f, new Multiplier(0.2f, new Sin(110))));
			Thread.sleep(100);
			mainPlayer.add(new DurationAdjuster(0.05f, new Multiplier(0.05f, new Square(315))));
		}
	}
}