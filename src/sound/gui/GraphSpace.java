package sound.gui;

import java.util.ArrayList;

import sound.math.Vector2D;

public class GraphSpace extends ArrayList<GraphObject>{
	private static final long serialVersionUID = 1L;
	
	public GraphSpace(){
		GraphObject g1 = new GraphObject();
		g1.pos = g1.pos.add(new Vector2D(-2,0));
		GraphObject g2 = new GraphObject();
		g2.pos = g2.pos.add(new Vector2D(2,0));
		add(g1);
		add(g2);		
	}
	
	public GraphObject getPointedObject(Vector2D p) {
		for(GraphObject go : this)
			if(go.contains(p))
				return go;
		return null;
	}

}
