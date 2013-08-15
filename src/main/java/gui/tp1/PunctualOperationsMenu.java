package gui.tp1;

import gui.Panel;
import gui.Window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import application.PunctualOperationsUtils;

@SuppressWarnings("serial")
public class PunctualOperationsMenu extends JMenu {

	public PunctualOperationsMenu() {
		super("Punctual operations");
		setEnabled(true);
		
		JMenuItem addition = new AddImagesItem(this);
		JMenuItem substraction = new SubstractImagesItem(this);
		JMenuItem multiplication = new MultiplyImagesItem(this);

		JMenuItem negative = new JMenuItem("Negative");
		negative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				panel.setImage(PunctualOperationsUtils.createInverse(panel.getImage()));
				panel.repaint();
			}
		});
		JMenuItem threshold = new JMenuItem("Threshold");
		threshold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog thresholdDialog = new ThresholdDialog(panel);
				thresholdDialog.setVisible(true);
			}
		});
		
		add(addition);
		add(substraction);
		add(multiplication);
		add(new JSeparator());
		add(negative);
		add(new JSeparator());
		add(threshold);
		add(new JSeparator());
	}

}
