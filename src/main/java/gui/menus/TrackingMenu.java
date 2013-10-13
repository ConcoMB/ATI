package gui.menus;

import gui.Panel;
import gui.Window;
import gui.tp3.HsvVideoTrackingDialog;
import gui.tp3.RgbImageTrackingDialog;
import gui.tp3.RgbVideoTrackingDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

@SuppressWarnings("serial")
public class TrackingMenu extends JMenu {

	public TrackingMenu() {
		super("Tracking");
		setEnabled(true);

		JMenuItem imageTracking = new JMenuItem("Image");
		imageTracking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog trackingDialog = new RgbImageTrackingDialog(panel);
				trackingDialog.setVisible(true);
				
			}
		});
		
		JMenuItem rgbVideoTracking = new JMenuItem("RGB Video");
		rgbVideoTracking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog trackingDialog = new RgbVideoTrackingDialog(panel);
				trackingDialog.setVisible(true);
				
			}
		});
		JMenuItem hvsVideoTracking = new JMenuItem("HVS Video");
		hvsVideoTracking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog trackingDialog = new HsvVideoTrackingDialog(panel);
				trackingDialog.setVisible(true);
				
			}
		});

		add(imageTracking);
		add(new JSeparator());
		add(rgbVideoTracking);
		add(hvsVideoTracking);
	}
}
