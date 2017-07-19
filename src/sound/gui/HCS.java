package sound.gui;

public class HCS {
	private boolean hovered;
	private boolean clicked;
	private boolean selected;

	public HCS() {
		hovered = false;
		clicked = false;
		selected = false;
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
