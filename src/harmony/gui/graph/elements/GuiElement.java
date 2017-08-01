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

	public Color backgroundColor;
	public Color color;

	public Color hoveredBackgroundColor;
	public Color hoveredColor;

	public Color clickedBackgroundColor;
	public Color clickedColor;

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
