package gui.menus;

import gui.Panel;
import gui.Window;
import gui.tp3.ImageTrackingDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class TrackingMenu extends JMenu {

	public TrackingMenu() {
		super("Tracking");
		setEnabled(true);

		JMenuItem imageTracking = new JMenuItem("Image tracking");
		imageTracking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog trackingDialog = new ImageTrackingDialog(panel);
				trackingDialog.setVisible(true);
				
			}
		});

		add(imageTracking);
	}
}
