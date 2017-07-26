package harmony;

import harmony.gui.DrawPanel;
import harmony.gui.MainFrame;
import harmony.sound.Concentrator;
import harmony.sound.generation.mix.Adder;
import harmony.sound.generation.mix.Delayer;
import harmony.sound.generation.mix.DurationAdjuster;
import harmony.sound.generation.mix.Multiplier;
import harmony.sound.generation.mix.Repeater;
import harmony.sound.generation.wave.Linear;
import harmony.sound.generation.wave.Sin;
import harmony.sound.generation.wave.Square;

public class MainTest {
	public static void main(String[] args) throws Exception {
		DrawPanel panel = new DrawPanel();
		new MainFrame(panel);

		Adder mainPlayer = new Adder();
		mainPlayer
				.add(new Repeater(2, new Multiplier(new Multiplier(0.3, new Sin(3)), new Sin(new Linear(30, 40, 2)))));

		Adder subAdder = new Adder();
		subAdder.add(new DurationAdjuster(1,
				new Multiplier(new Multiplier(0.05, new Sin(2)), new Square(new Linear(80, 100, 1)))));
		subAdder.add(new Delayer(2,
				new DurationAdjuster(2, new Multiplier(new Multiplier(0.05, new Sin(2)), new Square(100)))));

		mainPlayer.add(new Repeater(4, subAdder));

		Concentrator c = new Concentrator(mainPlayer);
		// c.listen();
	}
}