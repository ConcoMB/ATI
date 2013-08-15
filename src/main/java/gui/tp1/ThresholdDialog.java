package gui.tp1;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.MessageFrame;
import gui.Panel;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import application.PunctualOperationsUtils;

import domain.Image;

@SuppressWarnings("serial")
public class ThresholdDialog extends JDialog {

	public ThresholdDialog(final Panel panel) {
		setBounds(1, 1, 270, 190);
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 3 - getWidth() / 3, size.height / 3
				- getHeight() / 3);
		this.setResizable(false);
		setLayout(null);

		JPanel pan = new JPanel();
		pan.setBorder(BorderFactory.createTitledBorder("Value"));
		pan.setBounds(0, 0, 270, 60);
		JLabel label = new JLabel("Value = ");
		final JTextField field = new JTextField("128");
		field.setColumns(3);
		JButton okButton = new JButton("OK");
		okButton.setSize(270, 40);
		okButton.setBounds(0, 120, 270, 40);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int value;
				try {
					value = Integer.valueOf(field.getText().trim());
				} catch (NumberFormatException ex) {
					new MessageFrame("Invalid data");
					return;
				}
				if (value < 0 || value > Image.MAX_VAL) {
					new MessageFrame("Invalid Parameters");
					return;
				}
				Image original = panel.getImage();
				Image img = PunctualOperationsUtils.threshold(original, value);
				panel.loadImage(img);
				panel.repaint();
				dispose();
			}
		});
		pan.add(label);
		pan.add(field);
		add(pan);
		this.add(okButton);
	}
}
