package gui.tp0;

import gui.MessageFrame;
import gui.Panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import application.Creator;
import domain.Image;

public class DegradeDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	public DegradeDialog(final Panel panel, final boolean isColor) {

		setBounds(1, 1, 270, 190);
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 3 - getWidth() / 3, size.height / 3
				- getHeight() / 3);
		this.setResizable(false);
		setLayout(null);

		JPanel pan1 = new JPanel();
		pan1.setBorder(BorderFactory.createTitledBorder("Size"));
		pan1.setBounds(0, 0, 270, 60);

		JPanel pan2 = new JPanel();
		pan2.setBorder(BorderFactory.createTitledBorder("Color"));
		pan2.setBounds(0, 60, 270, 60);

		JLabel altoLabel = new JLabel("Height = ");
		final JTextField alto = new JTextField("0");
		alto.setColumns(3);

		JLabel anchoLabel = new JLabel("Width = ");
		final JTextField ancho = new JTextField("0");
		ancho.setColumns(3);

		String fieldText = "0";
		if (isColor) {
			fieldText = "000000";
			setTitle("Create color degradee");
		} else {
			setTitle("Create grey degradee");
		}

		JLabel colorLabel1 = new JLabel("From:");
		final JTextField color1 = new JTextField(fieldText);
		color1.setColumns(5);

		JLabel colorLabel2 = new JLabel("To:");
		final JTextField color2 = new JTextField(fieldText);
		color2.setColumns(5);

		JButton okButton = new JButton("OK");
		okButton.setSize(270, 40);
		okButton.setBounds(0, 120, 270, 40);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int height;
				int width;
				int c1;
				int c2;

				try {
					height = Integer.valueOf(alto.getText().trim());
					width = Integer.valueOf(ancho.getText().trim());
					if (isColor) {
						c1 = Integer.valueOf(color1.getText().trim(), 16);
						c2 = Integer.valueOf(color2.getText().trim(), 16);
					} else {
						c1 = Integer.valueOf(color1.getText().trim());
						c2 = Integer.valueOf(color2.getText().trim());
						c1 = new Color(c1, c1, c1).getRGB();
						c2 = new Color(c2, c2, c2).getRGB();
					}

				} catch (NumberFormatException ex) {
					new MessageFrame("Invalid data");
					return;
				}

				if (height <= 0 || width <= 0) {
					new MessageFrame("The image must be at least of 1x1");
					return;
				}
				Image img = Creator.createDegrade(isColor, height, width, c1,
						c2);
				if (img != null) {
					panel.loadImage(img);
					panel.repaint();
					dispose();
				} else {
					new MessageFrame("Invalid values");
					return;
				}

			}
		});

		pan1.add(altoLabel);
		pan1.add(alto);

		pan1.add(anchoLabel);
		pan1.add(ancho);

		pan2.add(colorLabel1);
		pan2.add(color1);
		pan2.add(colorLabel2);
		pan2.add(color2);

		this.add(pan1);
		this.add(pan2);
		this.add(okButton);

	};

}
