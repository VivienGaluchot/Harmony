package sound.gui;

import java.awt.Color;

import sound.math.Vector2D;

public abstract class HCS {
	
	public Color backgroundColor;
	public Color color;
	
	public Color hoveredBackgroundColor;
	public Color hoveredColor;
	
	public Color clickedBackgroundColor;
	public Color clickedColor;
	
	private boolean hovered;
	private boolean clicked;
	private boolean selected;
	
	public HCS() {
		this(Color.white, Color.red.darker());
	}

	public HCS(Color backgroundColor, Color color2) {
		this.backgroundColor = backgroundColor;
		this.color = color2;
		
		hoveredBackgroundColor = backgroundColor.darker();
		hoveredColor = color2.darker();
		
		clickedBackgroundColor = hoveredBackgroundColor.darker();
		clickedColor = hoveredColor.darker();
		
		hovered = false;
		clicked = false;
		selected = false;
	}
	
	public abstract boolean contains(Vector2D p);
	
	public Color getColor() {
		if (isClicked())
			return clickedColor;
		else if (isHovered())
			return hoveredColor;
		else
			return color;
	}
	
	public Color getBackgroundColor() {
		if (isClicked())
			return clickedBackgroundColor;
		else if (isHovered())
			return hoveredBackgroundColor;
		else
			return backgroundColor;
	}

	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}

	public boolean isHovered() {
		return hovered;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}
}
