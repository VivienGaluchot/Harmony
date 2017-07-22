package sound.gui;

import java.awt.Graphics;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import sound.math.Vector2D;

public class GraphSpace extends ArrayList<GraphObject> {
	private static final long serialVersionUID = 1L;

	public GraphSpace() {
		GraphObject g1 = new GraphObject();
		g1.pos = g1.pos.add(new Vector2D(-2, 0));
		GraphObject g2 = new GraphObject();
		g2.pos = g2.pos.add(new Vector2D(2, 0));
		add(g1);
		add(g2);
	}

	public Deque<HCS> getObjectDeque() {
		ArrayDeque<HCS> deque = new ArrayDeque<>();
		for (GraphObject go : this) {
			deque.add(go);
			for (DataPort dp : go.getInPorts())
				deque.add(dp);
			for (DataPort dp : go.getOutPorts())
				deque.add(dp);
		}

		for (GraphObject go : this) {
			for (DataPort dp : go.getInPorts())
				for (DataLink dl : dp.links)
					deque.add(dl);
		}
		return deque;
	}

	public HCS getPointedObject(Vector2D p) {
		Deque<HCS> deque = getObjectDeque();
		HCS hcs;
		do {
			hcs = deque.pollLast();
			if (hcs != null && hcs.contains(p))
				return hcs;
		} while (hcs != null);
		return null;
	}

	public synchronized void paint(Graphics g) {
		for (HCS hcs : getObjectDeque())
			hcs.paint(g);
	}

}
