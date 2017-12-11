package harmony.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;

import harmony.gui.base.HarmonyFrame;
import harmony.gui.graph.Space;
import harmony.processcore.code.SimpleProcessWriter;
import harmony.processcore.process.ProceduralUnit;

public class CodeFrame extends HarmonyFrame {
	private static final long serialVersionUID = 1L;
	
	private JTextPane textCode;
	private Space space;

	public CodeFrame(Space space) {
		super();
		
		this.space = space;
		
		setLayout(new GridBagLayout());
		updateTitle(space.getName());
		
		JButton update = new JButton("Update");
		update.setToolTipText("Open an existing project");
		update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				update();
			}
		});
		add(update, new GridBagConstraints(0, 0, 1, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

		textCode = new JTextPane();
		textCode.setEditable(false);
		textCode.setFont(new Font("Consolas", Font.PLAIN, 12));
		
		JScrollPane scroll = new JScrollPane(textCode);
		add(scroll, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

		setSize(new Dimension(600, 600));
		setLocationRelativeTo(null);

		scroll.getVerticalScrollBar().setValue(0);
	}
	
	public void update() {
		textCode.setText("");
		try {
			StringWriter swriter = new StringWriter();
			SimpleProcessWriter writer = new SimpleProcessWriter(swriter);
			ProceduralUnit spacePU = space.getProceduralUnit();
			if (spacePU != null) {
				writer.write(space.getProceduralUnit());
				textCode.setText(swriter.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
