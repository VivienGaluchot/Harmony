//Harmony : procedural sound waves generator
//Copyright (C) 2017  Vivien Galuchot
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, version 3 of the License.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.

package harmony.gui.base;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import harmony.Ressources;

public class HarmonyFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public HarmonyFrame() {
		super();
		
		updateTitle(null);
		List<Image> icons = new ArrayList<Image>();
		icons.add(Ressources.icon16.getImage());
		icons.add(Ressources.icon32.getImage());
		icons.add(Ressources.icon64.getImage());
		icons.add(Ressources.icon128.getImage());
		icons.add(Ressources.icon256.getImage());
		setIconImages(icons);

		setLayout(new GridBagLayout());
	}

	public void updateTitle(String title) {
		String mainTitle = "Harmony";
		if (title != null) {
			mainTitle += " - " + title;
		}
		setTitle(mainTitle);
	}
}
