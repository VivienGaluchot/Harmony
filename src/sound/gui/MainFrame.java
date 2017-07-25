package sound.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import sound.Licence;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private GraphPanel panel;

	public MainFrame(GraphPanel panel) {
		super();
		this.panel = panel;

		initMenu();

		setLayout(new GridBagLayout());
		getContentPane().setBackground(Color.white);

		add(new JLabel("-- Harmony --"), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
		add(panel, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(800, 600));
		setVisible(true);
		setLocationRelativeTo(null);
	}

	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();

		// FILE
		JMenu file = new JMenu("File");
		file.setMnemonic('F');

		JMenuItem open = new JMenuItem("Open");
		open.setToolTipText("Open an existing project");
		file.add(open);

		JMenuItem save = new JMenuItem("Save");
		save.setToolTipText("Save the current project");
		file.add(save);

		JMenuItem saveAs = new JMenuItem("Save as");
		saveAs.setToolTipText("Save the current project");
		file.add(saveAs);

		menuBar.add(file);

		// HELP
		JMenu help = new JMenu("Help");
		help.setMnemonic('H');

		JMenuItem licence = new JMenuItem("Licence");
		licence.setToolTipText("Software licence");
		licence.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						showLicence();
					}

				});
			}
		});
		help.add(licence);

		menuBar.add(help);

		setJMenuBar(menuBar);
	}

	private void showLicence() {
		JFrame licenceFrame = new JFrame();
		licenceFrame.setLayout(new GridBagLayout());

		licenceFrame.add(new JLabel("Harmony Software Copyright (C) 2017 Vivien Galuchot"), new GridBagConstraints(0, 0,
				1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
		JTextPane text = new JTextPane();
		text.setEditable(false);
		text.setText(Licence.licence);
		text.setFont(new Font("Consolas", Font.PLAIN, 12));
		JScrollPane scroll = new JScrollPane(text);
		licenceFrame.add(scroll, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		licenceFrame.setSize(new Dimension(550, 600));
		licenceFrame.setVisible(true);
		licenceFrame.setLocationRelativeTo(null);
	}
}
