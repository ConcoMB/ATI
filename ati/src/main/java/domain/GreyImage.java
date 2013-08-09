package domain;

import java.awt.image.BufferedImage;

import domain.Image.ImageFormat;

public class GreyImage extends Image {

	private Channel grey;

	public GreyImage(BufferedImage bi, ImageFormat format) {
		this(bi.getHeight(), bi.getWidth(), format);
//        setBufferedImage(bi);
//        initRgb(bi);
	}
	
	public GreyImage(int height, int width, ImageFormat format) {
		grey = new Channel(width, height);
		setHeight(height);
		setWidth(width);
		setFormat(format);
	}
	
	@Override
	public void setPixel(int x, int y, ColorChannel channel, double val) {
		if (!grey.validPixel(x, y)) {
			throw new IllegalArgumentException("Invalid pixels on setPixel");
		}
	}

	@Override
	public int getPixel(int x, int y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ImageType getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPixel(int x, int y, int rgb) {
		// TODO Auto-generated method stub
		
	}

}
