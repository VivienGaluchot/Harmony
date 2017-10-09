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

package harmony;

import java.net.URL;

import javax.swing.ImageIcon;

public class Ressources {
	public static ImageIcon icon16;
	public static ImageIcon icon32;
	public static ImageIcon icon64;
	public static ImageIcon icon128;
	public static ImageIcon icon256;
	
	public static void init() {
	    URL url16 = Ressources.class.getResource("/icon16.png");
	    URL url32 = Ressources.class.getResource("/icon32.png");
	    URL url64 = Ressources.class.getResource("/icon64.png");
	    URL url128 = Ressources.class.getResource("/icon128.png");
	    URL url256 = Ressources.class.getResource("/icon256.png");
	    icon16 = new ImageIcon(url16);
	    icon32 = new ImageIcon(url32);
	    icon64 = new ImageIcon(url64);
	    icon128 = new ImageIcon(url128);
	    icon256 = new ImageIcon(url256);
	}
}
