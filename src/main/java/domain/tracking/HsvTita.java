package domain.tracking;

import static domain.Image.ChannelType.HUE;
import static domain.Image.ChannelType.SATURATION;

import java.awt.Point;

import application.utils.ImageConversionUtils;
import domain.HsvImage;
import domain.Image;
import domain.Image.ChannelType;
import domain.RgbImage;

public class HsvTita extends Tita {

	public HsvTita(HsvImage image, Frontier frontier) {
		super(image, frontier);
	}

	@Override
	protected int getAvgIndex(ChannelType c) {
		switch (c) {
		case HUE:
			return 0;
		case SATURATION:
			return 1;
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	protected void removeFromOuter(int x, int y) {

		outerSum[0] -= image.getPixel(x, y, HUE);
		outerSum[1] -= image.getPixel(x, y, SATURATION);
		outerSize--;
	}

	@Override
	protected void removeFromInner(int x, int y) {
		innerSum[0] -= image.getPixel(x, y, HUE);
		innerSum[1] -= image.getPixel(x, y, SATURATION);
		innerSize--;
	}

	@Override
	protected void addToOuter(int x, int y) {
		outerSum[0] += image.getPixel(x, y, HUE);
		outerSum[1] += image.getPixel(x, y, SATURATION);
		outerSize++;
	}

	@Override
	protected void addToInner(int x, int y) {
		innerSum[0] += image.getPixel(x, y, HUE);
		innerSum[1] += image.getPixel(x, y, SATURATION);
		innerSize++;
	}

	@Override
	public double calculateVelocity(Point p) {
		double p1, p2;
		double hue = image.getPixel(p.x, p.y, HUE);
		double saturation = image.getPixel(p.x, p.y, SATURATION);

		p1 = Math.sqrt(Math.pow((averageInner(HUE) - hue), 2)
				+ Math.pow((averageInner(SATURATION) - saturation), 2));
		p2 = Math.sqrt(Math.pow((averageOuter(HUE) - hue), 2)
				+ Math.pow((averageOuter(SATURATION) - saturation), 2));

		return p2 - p1;
	}
	
	@Override
	public void setImage(Image image) {
		HsvImage hsv;
		if (image.isRgb()) 
			hsv = ImageConversionUtils.convertToHsv((RgbImage) image);
		else
			hsv = (HsvImage) image;
		super.setImage(hsv);
	}

}
