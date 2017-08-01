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

package harmony.math;

import java.awt.geom.Point2D;
import java.io.Serializable;

public class Vector2D implements Serializable {
	private static final long serialVersionUID = 1L;

	public double x;
	public double y;

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2D(Point2D p) {
		this.x = p.getX();
		this.y = p.getY();
	}

	public Point2D.Double toPoint2D() {
		return new Point2D.Double(x, y);
	}

	@Override
	public Vector2D clone() {
		return new Vector2D(x, y);
	}

	@Override
	public String toString() {
		return "Vector2D(" + x + "," + y + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Vector2D) {
			Vector2D v = (Vector2D) o;
			return v.x == x && v.y == y;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Double.hashCode(x) + Double.hashCode(y);
	}

	public Vector2D subtract(Vector2D vect) {
		return new Vector2D(x - vect.x, y - vect.y);
	}

	public double getLength() {
		return Math.sqrt(x * x + y * y);
	}

	public void capModule(double module) {
		double absModule = getLength();
		if (absModule > module) {
			double correctedSpeed = module / absModule;
			x = x * correctedSpeed;
			y = y * correctedSpeed;
		}
	}

	public Vector2D multiply(double d) {
		return new Vector2D(x * d, y * d);
	}

	public Vector2D add(Vector2D vect) {
		return new Vector2D(x + vect.x, y + vect.y);
	}

	public Vector2D normalize() {
		double length = getLength();
		if (length == 0)
			throw new ArithmeticException();
		return new Vector2D(x / length, y / length);
	}

	public double dot(Vector2D vect) {
		return x * vect.x + y * vect.y;
	}

	public Vector2D getOrthogonal() {
		Vector2D orth = new Vector2D(-y, x);
		return orth.normalize();
	}

	public boolean isNull() {
		return x == 0 && y == 0;
	}
}
