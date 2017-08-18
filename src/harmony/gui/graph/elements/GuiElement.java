//    Harmony : procedural sound waves generator
//    Copyright (C) 2017  Vivien Galuchot
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, version 3 of the License.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

package harmony.gui.graph.elements;

import java.awt.Color;
import java.awt.Graphics;

import harmony.gui.Types;
import harmony.math.Vector2D;

public abstract class GuiElement {

	private Color backgroundColor;
	private Color color;

	private Color hoveredBackgroundColor;
	private Color hoveredColor;

	private Color clickedBackgroundColor;
	private Color clickedColor;

	private boolean hovered;
	private boolean clicked;
	private boolean selected;

	public GuiElement() {
		this(Color.white, Color.black);
	}

	public GuiElement(Color backgroundColor, Color color2) {
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

	public abstract void paint(Graphics g);

	public abstract void handleCommand(Types.Command c);

	public Color getCurrentColor() {
		if (isClicked())
			return clickedColor;
		else if (isHovered())
			return hoveredColor;
		else
			return color;
	}

	public Color getCurrentBackgroundColor() {
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

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getHoveredBackgroundColor() {
		return hoveredBackgroundColor;
	}

	public void setHoveredBackgroundColor(Color hoveredBackgroundColor) {
		this.hoveredBackgroundColor = hoveredBackgroundColor;
	}

	public Color getHoveredColor() {
		return hoveredColor;
	}

	public void setHoveredColor(Color hoveredColor) {
		this.hoveredColor = hoveredColor;
	}

	public Color getClickedBackgroundColor() {
		return clickedBackgroundColor;
	}

	public void setClickedBackgroundColor(Color clickedBackgroundColor) {
		this.clickedBackgroundColor = clickedBackgroundColor;
	}

	public Color getClickedColor() {
		return clickedColor;
	}

	public void setClickedColor(Color clickedColor) {
		this.clickedColor = clickedColor;
	}
}
