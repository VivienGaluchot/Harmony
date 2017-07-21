package sound.gui;

import java.awt.Color;

public class HCS {
	
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
		backgroundColor = Color.white;
		color = Color.red.darker();
		
		hoveredBackgroundColor = backgroundColor.darker();
		hoveredColor = color.darker();
		
		clickedBackgroundColor = hoveredBackgroundColor.darker();
		clickedColor = hoveredColor.darker();
		
		hovered = false;
		clicked = false;
		selected = false;
	}
	
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
