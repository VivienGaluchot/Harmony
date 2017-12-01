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
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.geom.Ellipse2D;

import harmony.gui.Types;
import harmony.math.Vector2D;

public abstract class GuiElement {

	private Component father_component;
	private GuiElement father;

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
		this(null, null, Color.white, Color.black);
	}

	public GuiElement(Component father_component) {
		this(father_component, null, Color.white, Color.black);
	}

	public GuiElement(GuiElement father) {
		this(father, Color.white, Color.black);
	}

	public GuiElement(Component father_component, Color backgroundColor, Color color2) {
		this(father_component, null, backgroundColor, color2);
	}

	public GuiElement(GuiElement father, Color backgroundColor, Color color2) {
		this(null, father, backgroundColor, color2);
	}

	private GuiElement(Component father_component, GuiElement father, Color backgroundColor, Color color2) {
		this.father = father;
		this.father_component = father_component;

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

	public void setFather(GuiElement father) {
		if (this.father_component != null)
			throw new IllegalArgumentException("Component shouldn't have two fathers");
		this.father = father;
	}

	public void setFatherComponent(Component father_component) {
		if (this.father != null)
			throw new IllegalArgumentException("Component shouldn't have two fathers");
		this.father_component = father_component;
	}

	public GuiElement getFather() {
		return father;
	}

	public Component getFatherComponent() {
		GuiElement el = this;
		while (el.getFather() != null)
			el = getFather();
		return el.father_component;
	}

	public boolean contains(Vector2D p) {
		return selectionShape() != null && selectionShape().contains(p.x, p.y);
	}
	
	public abstract Shape selectionShape();

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

	// Util

	public static void drawResizedHCenteredString(Graphics2D g, String text, Vector2D pos, double size) {
		Graphics2D g2d = (Graphics2D) g.create();
		FontRenderContext frc = new FontRenderContext(g2d.getTransform(), true, true);
		double textWidth = g2d.getFont().getStringBounds(text, frc).getWidth();
		if (textWidth > 0) {
			double scale = size / textWidth;
			g2d.setFont(g2d.getFont().deriveFont((float) (g2d.getFont().getSize2D() * scale)));
			double x = pos.x - textWidth * scale / 2;
			g2d.drawString(text, (float) x, (float) (pos.y + 0.05 * scale));
		}
		g2d.dispose();
	}

	public static Ellipse2D getCenteredCircle(Vector2D pos, double radius) {
		return new Ellipse2D.Double(pos.x - radius, pos.y - radius, 2 * radius, 2 * radius);
	}
}
