package harmony.sound;

import javax.sound.sampled.LineUnavailableException;

import harmony.gui.graph.Space;
import harmony.processcore.data.DataArray;
import harmony.processcore.data.DataPattern;
import harmony.processcore.data.DataType;
import harmony.processcore.data.DataTypes;
import harmony.processcore.process.ComputeUnit;
import harmony.processcore.process.DefaultComputeUnit;
import harmony.processcore.process.HrmProcess;
import harmony.processcore.process.ProceduralUnit;
import harmony.sound.generation.SampleGenerator;

public class SoundGeneratorPanel extends Space {
	private static final long serialVersionUID = 1L;

	double globalTime = 0;
	Concentrator concentrator;

	private HrmProcess process;

	public SoundGeneratorPanel() {
		super();
		ProceduralUnit soundUnit = new ProceduralUnit("soundUnit", new DataPattern(new DataType[] { DataTypes.Double }),
				new DataPattern(new DataType[] { DataTypes.Double, DataTypes.Double }));
		
		ComputeUnit timeUnit = new DefaultComputeUnit("time", null,
				new DataPattern(new DataType[] { DataTypes.Double })) {
			@Override
			public DataArray compute(DataArray inputValues) {
				DataArray timeArray = new DataArray(this.getOutputPattern());
				timeArray.setValue(0, globalTime);
				return timeArray;
			}
		};
		
		process = new HrmProcess("soundProcess", soundUnit);
		process.setDependencie(0, new HrmProcess("time", timeUnit).getOutput(0));

		init("SoundGenerator", soundUnit);

		try {
			concentrator = new Concentrator(getSampleGenerator());
			concentrator.listen();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	private SampleGenerator getSampleGenerator() {
		SampleGenerator sampleGenerator = new SampleGenerator() {
			@Override
			public void reset() {
				globalTime = 0;
			}

			@Override
			public Sample next() {
				DataArray output = process.getValues();
				// TODO redesign time to avoid loss of precision
				globalTime = globalTime + 1 / (Sample.sampleRate);
				Double right = (Double) output.getValue(0);
				Double left = (Double) output.getValue(1);
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
