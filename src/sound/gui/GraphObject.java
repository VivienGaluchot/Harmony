package sound.gui;

import sound.math.Vector2D;

public class GraphObject extends HCS{
	
	public Vector2D pos;
	public Vector2D size;

	public GraphObject() {
		super();
		pos = new Vector2D(-3./2, -1);
		size = new Vector2D(3, 2);
	}

	public boolean contains(Vector2D p) {
		return pos.x <= p.x && pos.x + size.x >= p.x && pos.y <= p.y && pos.y + size.y >= p.y;
	}
}
