package gui.tp2.filters;

import gui.Panel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public abstract class AbstractBorderDetectorDialog extends JDialog {

	protected Panel panel;
	
	public enum SynthesizationType {
		MAX, MIN, AVG, ABS
	}

	
	public AbstractBorderDetectorDialog(final Panel panel, final String title) {
		setTitle(title);
		this.panel = panel;
		setBounds(1, 1, 450, 150);
		Dimension size = getToolkit().getScreenSize();
		setLocation(size.width/3 - getWidth()/3, size.height/3 - getHeight()/3);
		this.setResizable(false);
		setLayout(null);

		JPanel synthetizationPanel = new JPanel();
		synthetizationPanel.setBorder(BorderFactory.createTitledBorder("Sinthetization:"));
		synthetizationPanel.setBounds(0, 0, 450, 80);

		final JRadioButton absRadioButton = new JRadioButton("Module", true);
		final JRadioButton maxRadioButton = new JRadioButton("Maximum");
		final JRadioButton minRadioButton = new JRadioButton("Minimum");
		final JRadioButton avgRadioButton = new JRadioButton("Average");

		maxRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				maxRadioButton.setSelected(true);
				minRadioButton.setSelected(!maxRadioButton.isSelected());
				avgRadioButton.setSelected(!maxRadioButton.isSelected());
				absRadioButton.setSelected(!maxRadioButton.isSelected());
			}
		});

		minRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				minRadioButton.setSelected(true);
				maxRadioButton.setSelected(!minRadioButton.isSelected());
				avgRadioButton.setSelected(!minRadioButton.isSelected());
				absRadioButton.setSelected(!minRadioButton.isSelected());
			}
		});
		
		avgRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				avgRadioButton.setSelected(true);
				maxRadioButton.setSelected(!avgRadioButton.isSelected());
				minRadioButton.setSelected(!avgRadioButton.isSelected());
				absRadioButton.setSelected(!avgRadioButton.isSelected());
			}
		});

		absRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				absRadioButton.setSelected(true);
				maxRadioButton.setSelected(!absRadioButton.isSelected());
				minRadioButton.setSelected(!absRadioButton.isSelected());
				avgRadioButton.setSelected(!absRadioButton.isSelected());
			}
		});

		JButton okButton = new JButton("OK");
		okButton.setBounds(100, 80, 250, 40);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SynthesizationType synthetizationType = null;

				if(maxRadioButton.isSelected()) {
					synthetizationType = SynthesizationType.MAX;
				} else if(minRadioButton.isSelected()) {
					synthetizationType = SynthesizationType.MIN;
				} else if(avgRadioButton.isSelected()) {
					synthetizationType = SynthesizationType.AVG;
				} else if(absRadioButton.isSelected()) {
					synthetizationType = SynthesizationType.ABS;
				} else {
					throw new IllegalStateException();
				}
				
				AbstractBorderDetectorDialog.this.applyFunction(synthetizationType);
			}
		});

		synthetizationPanel.add(absRadioButton);
		synthetizationPanel.add(maxRadioButton);
		synthetizationPanel.add(minRadioButton);
		synthetizationPanel.add(avgRadioButton);

		this.add(synthetizationPanel);
		this.add(okButton);
	}
	
	abstract void applyFunction(SynthesizationType synthesizationType);
}