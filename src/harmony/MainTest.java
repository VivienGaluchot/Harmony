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

package harmony;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;

import harmony.data.DataDescriptor;
import harmony.data.DataGenerator;
import harmony.gui.MainFrame;
import harmony.gui.graph.Space;
import harmony.sound.Concentrator;
import harmony.sound.Sample;
import harmony.sound.SoundGeneratorPanel;
import harmony.sound.generation.SampleGenerator;
import harmony.sound.generation.mix.Adder;
import harmony.sound.generation.mix.Delayer;
import harmony.sound.generation.mix.DurationAdjuster;
import harmony.sound.generation.mix.Multiplier;
import harmony.sound.generation.mix.Repeater;
import harmony.sound.generation.wave.Linear;
import harmony.sound.generation.wave.Sin;
import harmony.sound.generation.wave.Square;

public class MainTest {
	static double globalTime = 0;
	static Concentrator concentrator;

	public static void main(String[] args) throws Exception {
		Ressources.init();
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		SoundGeneratorPanel sgp = new SoundGeneratorPanel();
		MainFrame frame = new MainFrame(sgp);

		// soundwave test
		Adder mainPlayer = new Adder();
		mainPlayer
				.add(new Repeater(2, new Multiplier(new Multiplier(0.3, new Sin(3)), new Sin(new Linear(30, 40, 2)))));

		Adder subAdder = new Adder();
		subAdder.add(new DurationAdjuster(1,
				new Multiplier(new Multiplier(0.05, new Sin(2)), new Square(new Linear(80, 100, 1)))));
		subAdder.add(new Delayer(2,
				new DurationAdjuster(2, new Multiplier(new Multiplier(0.05, new Sin(2)), new Square(100)))));

		mainPlayer.add(new Repeater(4, subAdder));

		// Concentrator c = new Concentrator(mainPlayer);
		// c.listen();
	}
}