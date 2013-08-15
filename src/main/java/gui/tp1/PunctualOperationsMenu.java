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
		JMenuItem negative = new JMenuItem("Negative");
		negative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				panel.setImage(PunctualOperationsUtils.createInverse(panel.getImage()));
				panel.repaint();
			}
		});
		JMenuItem threshold = new JMenuItem("Threshold");
		threshold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				JDialog thresholdDialog = new ThresholdDialog(panel);
				thresholdDialog.setVisible(true);
			}
		});
		
		add(new JSeparator());
		add(negative);
		add(new JSeparator());
		add(threshold);
		add(new JSeparator());
	}

}
