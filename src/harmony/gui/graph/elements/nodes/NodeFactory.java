package harmony.gui.graph.elements.nodes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import harmony.data.DataDescriptor;
import harmony.data.DataGenerator;
import harmony.data.ProcessScheme;
import harmony.gui.graph.Space;
import harmony.gui.graph.elements.Node;

public class NodeFactory {
	public static Node createTestNode(Space space) {
		ArrayList<ProcessScheme> schemes = new ArrayList<>();

		DataDescriptor x1 = new DataDescriptor() {
			@Override
			public Class<?> getDataClass() {
				return Double.class;
			}

			@Override
			public String getDataName() {
				return "X1";
			}
		};

		DataDescriptor x2 = new DataDescriptor() {
			@Override
			public Class<?> getDataClass() {
				return Double.class;
			}

			@Override
			public String getDataName() {
				return "X2";
			}
		};

		schemes.add(new ProcessScheme() {
			@Override
			public Class<?> getDataClass() {
				return Double.class;
			}

			@Override
			public String getDataName() {
				return "2*X1 + 1";
			}

			@Override
			public Set<DataDescriptor> getDependencies() {
				Set<DataDescriptor> dep = new HashSet<>();
				dep.add(x1);
				return dep;
			}

			@Override
			public Object process(Map<DataDescriptor, DataGenerator> generatorMap) {
				DataGenerator x1gen = generatorMap.get(x1);
				Double x1value = (Double) x1gen.getData();
				if (x1value != null)
					return x1value * 2.0 + 1.0;
				else
					return null;
			}
		});

		schemes.add(new ProcessScheme() {
			@Override
			public Class<?> getDataClass() {
				return Double.class;
			}

			@Override
			public String getDataName() {
				return "X1 + X2";
			}

			@Override
			public Set<DataDescriptor> getDependencies() {
				Set<DataDescriptor> dep = new HashSet<>();
				dep.add(x1);
				dep.add(x2);
				return dep;
			}

			@Override
			public Object process(Map<DataDescriptor, DataGenerator> generatorMap) {
				DataGenerator x1gen = generatorMap.get(x1);
				DataGenerator x2gen = generatorMap.get(x2);
				Double x1value = (Double) x1gen.getData();
				Double x2value = (Double) x2gen.getData();
				if (x1value != null && x2value != null)
					return x1value + x2value;
				else
					return null;
			}
		});

		Node n = new ProcessNode(space, "TestNode", schemes);
		return n;
	}
}
