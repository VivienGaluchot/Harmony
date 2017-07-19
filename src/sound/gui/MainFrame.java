package sound.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainFrame extends JFrame{
	private static final long serialVersionUID = 1L;

	public MainFrame(){
		super();
		setLayout(new GridBagLayout());
		getContentPane().setBackground(Color.white);
		
		add(new JLabel("-- Harmony --"), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
		add(new GraphPanel(), new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(600, 500));
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
}
