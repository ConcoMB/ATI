package domain.tracking;

import java.awt.Point;

import application.utils.ImageConversionUtils;
import domain.HsvImage;
import domain.Image;
import domain.RgbImage;

public class RgbFrontier extends Frontier {

	public RgbFrontier(Point p, Point q, RgbImage image) {
		super(p, q, (Image)image);
	}

	@Override
	public void setImage(Image image) {
		if (image.isHsv()) {
			image = ImageConversionUtils.convertToRgb((HsvImage) image);
		}
		tita.setImage((RgbImage) image);
	}
}
