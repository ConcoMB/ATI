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
	protected void track(int px, int py, int qx, int qy, int iterations) {
		dispose();
		String[] splitted = Loader.getCurrentFile().getAbsolutePath()
				.split("/");
		String fileName = splitted[splitted.length-1];
		int x = fileName.indexOf('.');
		
		StringBuffer num = new StringBuffer();
		boolean end = false;
		while (!end && x > 0) {
			char n = fileName.charAt(--x);
			if ( n>= '0' && n <= '9')
				num.append(n);
			else 
				end = true;
		}
		num.reverse();
		String[] nameSplitted = Loader.getCurrentFile().getAbsolutePath().split(num.toString() + ".");
		if (nameSplitted.length < 2) {
			new MessageFrame("Invalid Image");
			return;
		}
		String extension = nameSplitted[1];
		String  filePrefix = nameSplitted[0];
		Frontier frontier = getFrontier(px, py, qx, qy);
		TrackingUtils.track(frontier, panel, iterations);
		panel.repaint();
		int i = Integer.valueOf(num.toString());
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
				TrackingUtils.track(frontier, panel, iterations);
			} catch (Exception e) {
				e.printStackTrace();
			}
			i++;
		}

	}

	protected abstract Frontier getFrontier(int px, int py, int qx, int qy);
}
