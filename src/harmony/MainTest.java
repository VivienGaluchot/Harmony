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

import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;

import harmony.data.DataDescriptor;
import harmony.data.DataGenerator;
import harmony.gui.MainFrame;
import harmony.gui.graph.Space;
import harmony.sound.Concentrator;
import harmony.sound.Sample;
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
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		List<DataGenerator> inputs = new ArrayList<>();
		inputs.add(new DataGenerator() {
			@Override
			public Class<?> getDataClass() {
				return Double.class;
			}

			@Override
			public String getDataName() {
				return "Time (s)";
			}

			@Override
			public Object getData() {
				return globalTime;
			}
		});

		DataDescriptor rightSignal = new DataDescriptor() {
			@Override
			public Class<?> getDataClass() {
				return Double.class;
			}

			@Override
			public String getDataName() {
				return "Right signal";
			}
		};

		DataDescriptor leftSignal = new DataDescriptor() {
			@Override
			public Class<?> getDataClass() {
				return Double.class;
			}

			@Override
			public String getDataName() {
				return "Left signal";
			}
		};

		List<DataDescriptor> outputs = new ArrayList<>();
		outputs.add(rightSignal);
		outputs.add(leftSignal);
		// Create computing space
		Space space = new Space("Main", inputs, outputs);
		// Display computing space gui
		new MainFrame(space);
		// Create SampleGenerator
		SampleGenerator sampleGenerator = new SampleGenerator() {
			@Override
			public void reset() {
				globalTime = 0;
			}

			@Override
			public Sample next() {
				// TODO redesign to avoid loss of precision
				globalTime = globalTime + 1 / (Sample.sampleRate);
				Object rightObject = space.getOutputNode().getData(rightSignal);
				Object leftObject = space.getOutputNode().getData(leftSignal);
				double right = rightObject != null ? (double) rightObject : 0.0;
				double left = leftObject != null ? (double) leftObject : 0.0;
				return new Sample(right, left);
			}

			@Override
			public boolean hasNext() {
				return true;
			}
		};
		// Play
		concentrator = new Concentrator(sampleGenerator);
		concentrator.listen();

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