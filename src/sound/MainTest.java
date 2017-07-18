package sound;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import sound.generation.mix.DurationAdjuster;
import sound.generation.mix.Multiplier;
import sound.generation.mix.Repeater;
import sound.generation.mix.Adder;
import sound.generation.wave.Linear;
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
		mainPlayer.add(new Repeater(2, new Multiplier(new Multiplier(0.3, new Sin(3)), new Sin(new Linear(50,80,2)))));
		mainPlayer.add(new DurationAdjuster(10, new Multiplier(new Multiplier(0.05, new Sin(2)), new Square(80))));

		Concentrator c = new Concentrator(mainPlayer);
		c.listen();
	}
}