package gui.tp3;

import gui.MessageFrame;
import gui.Panel;

import java.io.File;

import application.Loader;
import application.utils.TrackingUtils;
import domain.tracking.Frontier;

@SuppressWarnings("serial")
public abstract class VideoTrackingDialog extends TrackingDialog {

	public VideoTrackingDialog(Panel panel) {
		super(panel);
	}

	@Override
	protected void track(int px, int py, int qx, int qy) {
		dispose();
		int i = 30231;
		String[] splitted = Loader.getCurrentFile().getAbsolutePath()
				.split(i + ".");
		if (splitted.length > 2) {
			
			new MessageFrame("Invalid file");
			System.out.println(splitted[0]);
			return;
		}
		String filePrefix = splitted[0];
		String extension = splitted[1];
		Frontier frontier = getFrontier(px, py, qx, qy);
		TrackingUtils.track(frontier, panel);
		panel.repaint();
		i++;
		boolean read = true;
		int width = panel.getImage().getWidth();
		int height = panel.getImage().getHeight();
		while (read) {
			try {
				File currentFile = new File(filePrefix + i + "." + extension);
				if (!currentFile.exists()) {
					read = false;
					break;
				}
				panel.setTempImage(Loader.loadImage(currentFile));
				frontier.setImage(panel.getTempImage());
				panel.paintImmediately(0, 0, width, height);
				TrackingUtils.track(frontier, panel);
			} catch (Exception e) {
				e.printStackTrace();
			}
			i++;
		}

	}

	protected abstract Frontier getFrontier(int px, int py, int qx, int qy);
}
