package gui.menus;

import gui.Panel;
import gui.Window;
import gui.tp1.punctual.ThresholdDialog;
import gui.tp2.thresholds.ColorGlobalThresholdDialog;
import gui.tp2.thresholds.GlobalThresholdDialog;
import gui.tp3.HysteresisThresholdDialog;
import gui.tpf.AwesomeVideoActionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import application.utils.ThresholdUtils;
import domain.Image;
import domain.Image.ImageType;

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
		
		JMenuItem globalThreshold = new JMenuItem("Global");
		globalThreshold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				Image image = panel.getImage();
				if (image == null) {
					return;
				}
				if (image.getType() == ImageType.GREYSCALE) {
					JDialog otsuThresholdDialog = new GlobalThresholdDialog(panel);
					otsuThresholdDialog.setVisible(true);					
				} else {
					JDialog otsuThresholdDialog = new ColorGlobalThresholdDialog(panel);
					otsuThresholdDialog.setVisible(true);					
				}
			}
		});
		
		JMenuItem otsuThreshold = new JMenuItem("Otsu");
		otsuThreshold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				panel.setImage(ThresholdUtils.otsu(panel.getImage()));
				panel.repaint();
			}
		});
		
		JMenuItem hysteresis = new JMenuItem("Hysteresis");
		hysteresis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog dialog = new HysteresisThresholdDialog(panel);
				dialog.setVisible(true);
			}
		});
		

		JMenu videoThresholding = new JMenu("Video Thresholding");

		JMenuItem awesomeImage = new JMenuItem("Single Image");
		JMenuItem awesomeVideo = new JMenuItem("Video");

		awesomeImage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				Image image = panel.getImage();
				if (image == null) {
					return;
				}
				panel.setImage(ThresholdUtils.awesomeThresholding(image));
				panel.repaint();
			}
		});
		
		awesomeVideo.addActionListener(new AwesomeVideoActionListener(this));

		videoThresholding.add(awesomeImage);
		videoThresholding.add(awesomeVideo);
		add(threshold);
		add(globalThreshold);
		add(otsuThreshold);
		add(hysteresis);
		add(new JSeparator());
		add(videoThresholding);

	}
}
