package domain.tracking;

import static domain.Image.ChannelType.BLUE;
import static domain.Image.ChannelType.GREEN;
import static domain.Image.ChannelType.RED;
import static domain.tracking.Frontier.Side.INNER;
import static domain.tracking.Frontier.Side.OUTER;

import java.awt.Point;

import application.utils.ImageConversionUtils;

import domain.Image.ChannelType;
import domain.HsvImage;
import domain.Image;
import domain.RgbImage;

public class RgbFrontier extends Frontier {

	// Arrays x color: 0 -> RED, 1 -> GREEN, 2 -> BLUE
	private double[] innerSum;
	private double[] outerSum;

	public RgbFrontier(Point p, Point q, RgbImage image) {
		super(p, q, image);
		innerSum = new double[3];
		outerSum = new double[3];
		innerSum[0] = sum(RED, INNER);
		innerSum[1] = sum(GREEN, INNER);
		innerSum[2] = sum(BLUE, INNER);
		outerSum[0] = sum(RED, OUTER);
		outerSum[1] = sum(GREEN, OUTER);
		outerSum[2] = sum(BLUE, OUTER);
	}

	@Override
	protected void removeFromOuterAndRecalculate(Point p) {
		if (outer.remove(p)) {
			outerSum[0] -= image.getPixel(p, RED);
			outerSum[1] -= image.getPixel(p, GREEN);
			outerSum[2] -= image.getPixel(p, BLUE);
		}
	}

	@Override
	protected void removeFromInnerAndRecalculate(Point p) {
		if (inner.remove(p)) {
			innerSum[0] -= image.getPixel(p, RED);
			innerSum[1] -= image.getPixel(p, GREEN);
			innerSum[2] -= image.getPixel(p, BLUE);
		}
	}

	@Override
	protected void addToOuterAndRecalculate(Point p) {
		if (outer.add(p)) {
			outerSum[0] += image.getPixel(p, RED);
			outerSum[1] += image.getPixel(p, GREEN);
			outerSum[2] += image.getPixel(p, BLUE);
		}
	}

	@Override
	protected void addToInnerAndRecalculate(Point p) {
		if (inner.add(p)) {
			innerSum[0] += image.getPixel(p, RED);
			innerSum[1] += image.getPixel(p, GREEN);
			innerSum[2] += image.getPixel(p, BLUE);
		}
	}
	
	public double averageInner(ChannelType channel) {
		return innerSum[getAvgIndex(channel)] / inner.size();
	}

	public double averageOuter(ChannelType channel) {
		return outerSum[getAvgIndex(channel)] / outer.size();
	}
	
	private int getAvgIndex(ChannelType c) {
		switch (c) {
		case RED:
			return 0;
		case GREEN:
			return 1;
		case BLUE:
			return 2;
		default:
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	public void setImage(Image image) {
		if(image.isHsv()) {
			image = ImageConversionUtils.convertToRgb((HsvImage) image);
		}
		super.image = (RgbImage) image;
	}
}
