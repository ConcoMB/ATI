package gui.tp3;

import gui.MessageFrame;
import gui.Panel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import application.utils.FilterUtils;
import domain.Image;

@SuppressWarnings("serial")
public class SusanDialog extends JDialog {

	public SusanDialog(final Panel panel) {

		setTitle("Susan");
		setBounds(1, 1, 250, 150);
		Dimension size = getToolkit().getScreenSize();
		setLocation(size.width / 3 - getWidth() / 3, size.height / 3
				- getHeight() / 3);
		this.setResizable(false);
		setLayout(null);

		JPanel pan = new JPanel();
		pan.setBounds(0, 0, 250, 75);

		JLabel minLabel = new JLabel("Min = ");
		final JTextField minTextField = new JTextField("0.5");
		minTextField.setColumns(5);
		JLabel maxLabel = new JLabel("Max = ");
		final JTextField maxTextField = new JTextField("0.75");
		maxTextField.setColumns(5);

		JButton okButton = new JButton("OK");
		okButton.setSize(250, 40);
		okButton.setBounds(0, 75, 250, 40);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double min = 0.5;
				double max = 0.75;
				try {
					min = Double.valueOf(minTextField.getText());
					max = Double.valueOf(maxTextField.getText());
				} catch (NumberFormatException ex) {
					new MessageFrame("Invalid values");
					return;
				}

				if (min >= max || max >= 1 || min <= 0) {
					new MessageFrame("Invalid values");
					return;
				}
				Image susaned = FilterUtils.applySusanFilter(panel.getImage(),
						min, max);
				panel.setImage(susaned);
				panel.repaint();
				dispose();
			}
		});

		pan.add(minLabel);
		pan.add(minTextField);
		pan.add(maxLabel);
		pan.add(maxTextField);

		this.add(pan);
		this.add(okButton);

	}

}
