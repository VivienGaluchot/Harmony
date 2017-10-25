package harmony.sound;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;

import harmony.dataprocess.model.DataDescriptor;
import harmony.dataprocess.model.DataGenerator;
import harmony.gui.graph.Space;
import harmony.sound.generation.SampleGenerator;

public class SoundGeneratorPanel extends Space {
	private static final long serialVersionUID = 1L;

	double globalTime = 0;
	Concentrator concentrator;

	private DataDescriptor rightSignal;
	private DataDescriptor leftSignal;

	public SoundGeneratorPanel() {
		super();
		init("SoundGenerator", createInputs(), createOutputs());

		try {
			concentrator = new Concentrator(getSampleGenerator());
			concentrator.listen();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	private List<DataGenerator> createInputs() {
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
		return inputs;
	}

	private List<DataDescriptor> createOutputs() {
		List<DataDescriptor> outputs = new ArrayList<>();
		rightSignal = new DataDescriptor() {
			@Override
			public Class<?> getDataClass() {
				return Double.class;
			}

			@Override
			public String getDataName() {
				return "Right signal";
			}
		};

		leftSignal = new DataDescriptor() {
			@Override
			public Class<?> getDataClass() {
				return Double.class;
			}

			@Override
			public String getDataName() {
				return "Left signal";
			}
		};
		outputs.add(rightSignal);
		outputs.add(leftSignal);
		return outputs;
	}

	private SampleGenerator getSampleGenerator() {
		SampleGenerator sampleGenerator = new SampleGenerator() {
			@Override
			public void reset() {
				globalTime = 0;
			}

			@Override
			public Sample next() {
				// TODO redesign time to avoid loss of precision
				globalTime = globalTime + 1 / (Sample.sampleRate);
				Object rightObject = getOutputNode().getData(rightSignal);
				Object leftObject = getOutputNode().getData(leftSignal);
				double right = rightObject != null ? (double) rightObject : 0.0;
				double left = leftObject != null ? (double) leftObject : 0.0;
				return new Sample(right, left);
			}

			@Override
			public boolean hasNext() {
				return true;
			}
		};
		return sampleGenerator;
	}

}
