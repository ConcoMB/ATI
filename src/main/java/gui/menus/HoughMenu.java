package gui.menus;

import gui.Panel;
import gui.Window;
import gui.tp1.punctual.ThresholdDialog;
import gui.tp3.HoughCircleDialog;
import gui.tp3.HoughCircleDialog;
import gui.tp3.HoughLineDialog;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import application.utils.TrackingUtils;
import domain.tracking.Frontier;

public class HoughMenu extends JMenu{
	
	private boolean between(int m, int a, int b) {
		int min = Math.min(a, b);
		int max = Math.max(a, b);
		return m >= min && m <= max;
	}

	public HoughMenu() {
		super("Hough");
		setEnabled(true);

		JMenuItem lineDetector = new JMenuItem("Hough Line Detector");
		lineDetector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog houghLineDialog = new HoughLineDialog(panel);
				houghLineDialog.setVisible(true);
			}
		});
		
		JMenuItem circleDetector = new JMenuItem("Hough Circle Detector");
		circleDetector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog houghCircleDialog = new HoughCircleDialog(panel);
				houghCircleDialog.setVisible(true);
			}
		});

		add(lineDetector);
		add(circleDetector);
	}

}
