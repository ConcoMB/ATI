package gui.tp3;

import gui.Panel;

import java.awt.Point;

import application.utils.ImageConversionUtils;
import application.utils.TrackingUtils;
import domain.HsvImage;
import domain.Image;
import domain.RgbImage;
import domain.tracking.RgbFrontier;

@SuppressWarnings("serial")
public class RgbImageTrackingDialog extends TrackingDialog {

	public RgbImageTrackingDialog(final Panel panel) {
		super(panel);
	}

	@Override
	protected void track(int px, int py, int qx, int qy) {
		Image image = panel.getImage();
		RgbImage rgb;
		if (image.isHsv())
			rgb = ImageConversionUtils.convertToRgb((HsvImage) image);
		else
			rgb = (RgbImage) image;
		TrackingUtils.track(new RgbFrontier(new Point(px, py),
				new Point(qx, qy), rgb), panel);
		panel.repaint();
		dispose();
	}

}
