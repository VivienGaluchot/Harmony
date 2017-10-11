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

package harmony.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import harmony.License;
import harmony.gui.graph.Space;

public class MainFrame extends SpaceEditFrame {
	private static final long serialVersionUID = 1L;

	public MainFrame(Space space) {
		super(space);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	protected void initMenu() {
		super.initMenu();

		// HELP
		JMenu help = new JMenu("Help");
		help.setMnemonic('H');

		JMenuItem license = new JMenuItem("License");
		license.setToolTipText("Software license");
		license.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showLicense();
			}
		});
		help.add(license);

		menuBar.add(help);
	}

	private void showLicense() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame licenseFrame = new JFrame();
				licenseFrame.setLayout(new GridBagLayout());
				licenseFrame.setTitle("License");

				licenseFrame.add(new JLabel("Harmony, license informations"), new GridBagConstraints(0, 0, 1, 1, 0, 0,
						GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
				JTextPane pref = new JTextPane();
				pref.setEditable(false);
				pref.setText("Harmony : procedural sound waves generator\n" + "Copyright (C) 2017 - Vivien Galuchot\n"
						+ "\n"
						+ "This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, version 3 of the License.\n"
						+ "\n"
						+ "This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.");
				pref.setOpaque(false);
				licenseFrame.add(pref, new GridBagConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

				JTextPane textLisence = new JTextPane();
				textLisence.setEditable(false);
				textLisence.setText(License.license);
				textLisence.setFont(new Font("Consolas", Font.PLAIN, 12));
				JScrollPane scroll = new JScrollPane(textLisence);
				licenseFrame.add(scroll, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

				licenseFrame.setSize(new Dimension(600, 600));
				licenseFrame.setVisible(true);
				licenseFrame.setLocationRelativeTo(null);

				scroll.getVerticalScrollBar().setValue(0);

				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						scroll.getVerticalScrollBar().setValue(0);
					}
				});
			}
		});
	}
}
