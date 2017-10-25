package harmony.dataprocess;

import java.util.HashSet;
import java.util.Set;

import harmony.dataprocess.implem.DataGeneratorModel;
import harmony.dataprocess.implem.DataProcessorModel;
import harmony.dataprocess.model.DataGenerator;
import harmony.dataprocess.model.DataProcessor;

public class DataProcessTest {
	public static void main(String[] args) throws Exception {
		DataGenerator a = new DataGeneratorModel(Double.class, "a", new Double(1.0));
		DataGenerator b = new DataGeneratorModel(Double.class, "b", new Double(1.0));
		System.out.println(a);
		System.out.println(b);
		
		Set<DataGenerator> dep = new HashSet<DataGenerator>();
		dep.add(a);
		dep.add(b);
		DataProcessor pr = new DataProcessorModel(Double.class, "b", new Double(1.0), dep);
		System.out.println(pr);
	}
}
