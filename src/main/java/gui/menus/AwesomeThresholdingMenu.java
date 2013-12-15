package gui.menus;

import gui.Panel;
import gui.Window;
import gui.tpf.DoNothingVideoActionListener;
import gui.tpf.NotMergedAwersomeVideoActionListener;
import gui.tpf.SimplyAwesomeVideoActionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import application.utils.ThresholdUtils;
import domain.Image;

@SuppressWarnings("serial")
public class AwesomeThresholdingMenu extends JMenu {

	public AwesomeThresholdingMenu() {
		super("Video threshold");
		setEnabled(true);

		JMenuItem awesomeImage = new JMenuItem("Single Image");
		JMenuItem awesomeImageNotMerged = new JMenuItem(
				"Unmerged Single Image");

		JMenuItem awesomeVideo = new JMenuItem("Video");
		JMenuItem unmerged = new JMenuItem("Unmerged video");

		JMenuItem doNothingVideo = new JMenuItem("Simple video player");

		awesomeImageNotMerged.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				Image image = panel.getImage();
				if (image == null) {
					return;
				}
				panel.setImage(ThresholdUtils
						.notMergedAwesomeThresholding(image));
				panel.repaint();
			}
		});

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

		awesomeVideo.addActionListener(new SimplyAwesomeVideoActionListener(
				this));
		unmerged.addActionListener(new NotMergedAwersomeVideoActionListener(
				this));
		doNothingVideo
				.addActionListener(new DoNothingVideoActionListener(this));

		add(awesomeImage);
		add(awesomeImageNotMerged);
		add(new JSeparator());
		add(awesomeVideo);
		add(unmerged);
		add(new JSeparator());
		add(doNothingVideo);
	}
}