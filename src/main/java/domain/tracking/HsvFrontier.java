package domain.tracking;

import static domain.Image.ChannelType.HUE;
import static domain.Image.ChannelType.SATURATION;
import static domain.tracking.Frontier.Side.INNER;
import static domain.tracking.Frontier.Side.OUTER;

import java.awt.Point;

import application.utils.ImageConversionUtils;

import domain.HsvImage;
import domain.Image;
import domain.Image.ChannelType;
import domain.RgbImage;

public class HsvFrontier{ //extends Frontier {
	// Arrays x color: 0 -> HUE, 1 -> SATURATION; VALUE se ignora
//	private double[] innerSum;
//	private double[] outerSum;
//
//	public HsvFrontier(Point p, Point q, HsvImage image) {
//		super(p, q, image);
//		innerSum = new double[2];
//		outerSum = new double[2];
//		innerSum[0] = sum(HUE, INNER);
//		innerSum[1] = sum(SATURATION, INNER);
//		outerSum[0] = sum(HUE, OUTER);
//		outerSum[1] = sum(SATURATION, OUTER);
//	}
//
//	@Override
//	protected void removeFromOuterAndRecalculate(Point p) {
//		if (outer.remove(p)) {
//			outerSum[0] -= image.getPixel(p, HUE);
//			outerSum[1] -= image.getPixel(p, SATURATION);
//		}
//	}
//
//	@Override
//	public double velocity(Point p) {
//		double p1, p2;
//		double hue = image.getPixel(p.x, p.y, HUE);
//		double sat = image.getPixel(p.x, p.y, SATURATION);
//
//		p1 = Math.sqrt(Math.pow((averageInner(HUE) - hue), 2)
//				+ Math.pow((averageInner(SATURATION) - sat), 2));
//		p2 = Math.sqrt(Math.pow((averageOuter(HUE) - hue), 2)
//				+ Math.pow((averageOuter(SATURATION) - sat), 2));
//	return p2 - p1;	
//	}
//	
//	@Override
//	protected void removeFromInnerAndRecalculate(Point p) {
//		if (inner.remove(p)) {
//			innerSum[0] -= image.getPixel(p, HUE);
//			innerSum[1] -= image.getPixel(p, SATURATION);
//		}
//	}
//
//	@Override
//	protected void addToOuterAndRecalculate(Point p) {
//		if (outer.add(p)) {
//			outerSum[0] += image.getPixel(p, HUE);
//			outerSum[1] += image.getPixel(p, SATURATION);
//		}
//	}
//
//	@Override
//	protected void addToInnerAndRecalculate(Point p) {
//		if (inner.add(p)) {
//			innerSum[0] += image.getPixel(p, HUE);
//			innerSum[1] += image.getPixel(p, SATURATION);
//		}
//	}
//
//	public double averageInner(ChannelType channel) {
//		return innerSum[getAvgIndex(channel)] / inner.size();
//	}
//
//	public double averageOuter(ChannelType channel) {
//		return outerSum[getAvgIndex(channel)] / outer.size();
//	}
//
//	private int getAvgIndex(ChannelType c) {
//		switch (c) {
//		case HUE:
//			return 0;
//		case SATURATION:
//			return 1;
//		default:
//			throw new IllegalArgumentException();
//		}
//	}
//
//	@Override
//	public void setImage(Image image) {
//		if(image.isRgb()) {
//			image = ImageConversionUtils.convertToHsv((RgbImage) image);
//		}
//		super.image = (HsvImage) image;
//	}

}
