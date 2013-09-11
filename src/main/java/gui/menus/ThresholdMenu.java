package gui.menus;

import gui.Panel;
import gui.Window;
import gui.tp1.punctual.ThresholdDialog;
import gui.tp2.thresholds.GlobalThresholdDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import application.utils.ThresholdUtils;

@SuppressWarnings("serial")
public class ThresholdMenu extends JMenu {

	public ThresholdMenu() {
		super("Threshold");
		setEnabled(true);
		
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
		
		JMenuItem globalThreshold = new JMenuItem("Global Threshold");
		globalThreshold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog otsuThresholdDialog = new GlobalThresholdDialog(panel);
				otsuThresholdDialog.setVisible(true);
			}
		});
		
		JMenuItem otsuThreshold = new JMenuItem("Otsu Threshold");
		otsuThreshold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				panel.setImage(ThresholdUtils.otsuThreshold(panel.getImage()));
				panel.repaint();
			}
		});
		
		add(threshold);
		add(globalThreshold);
		add(otsuThreshold);

	}
}
