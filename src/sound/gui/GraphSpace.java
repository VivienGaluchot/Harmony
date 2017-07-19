package sound.gui;

import java.util.ArrayList;

import sound.math.Vector2D;

public class GraphSpace extends ArrayList<GraphObject>{
	private static final long serialVersionUID = 1L;
	
	public GraphSpace(){
		
	}
	
	public GraphObject getPointedObject(Vector2D p) {
		for(GraphObject go : this)
			if(go.contains(p))
				return go;
		return null;
	}

}
