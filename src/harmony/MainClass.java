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

import javax.swing.UIManager;

import harmony.gui.MainFrame;
import harmony.sound.SoundGeneratorPanel;

public class MainClass {
	public static void main(String[] args) throws Exception {
		Ressources.init();
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		SoundGeneratorPanel sgp = new SoundGeneratorPanel();
		new MainFrame(sgp);
	}
}