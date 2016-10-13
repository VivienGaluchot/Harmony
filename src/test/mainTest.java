package test;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class mainTest {
	public static void main(String[] args) throws Exception {
		final JFrame frame = new JFrame();
		final JLabel label = new JLabel("Harmony");
		frame.add(label);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

		Concentrateur c = new Concentrateur();
		c.listen();

		while (true) {
			Thread.sleep(100);
			c.addGenerateur(new SinWave(50, 50, 1.5f));
			Thread.sleep(100);
			c.addGenerateur(new SinWave(100, 20, 1f));
			Thread.sleep(100);
			c.addGenerateur(new SinWave(300, 20, 0.5f));
			Thread.sleep(100);
			c.addGenerateur(new SquareWave(300, 2, 0.1f));
			c.addGenerateur(new SquareWave(1200, 2, 0.4f));
			Thread.sleep(200);
			c.addGenerateur(new SquareWave(300, 2, 0.1f));
			Thread.sleep(200);
			c.addGenerateur(new SquareWave(300, 2, 0.1f));
			Thread.sleep(200);
			c.addGenerateur(new SquareWave(300, 2, 0.1f));
			Thread.sleep(1600);
		}
	}
}