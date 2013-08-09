package domain;

import java.awt.image.BufferedImage;

public abstract class Image implements Cloneable {

	public static enum ColorChannel { RED, GREEN, BLUE }
	public static enum ImageType { COLOR, GREY }
	public static enum ImageFormat { BMP, PGM, PPM, RAW }
	public static final int GREY_MAX = 255;

	private ImageFormat format;
	private int height;
	private int width;
	private BufferedImage bufferedImage;
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	protected void setHeight(int height) {
		this.height = height;
	}

	public ImageFormat getFormat() {
		return format;
	}

	public void setFormat(ImageFormat format) {
		this.format = format;
	}

	protected void setWidth(int width) {
		this.width = width;
	}

	public ImageFormat getImageFormat() {
		return format;
	}
	
    public BufferedImage getBufferedImage() {
    	return bufferedImage;
    }

    protected void setBufferedImage(BufferedImage bi) {
    	bufferedImage = bi;
    }
	
	public abstract void setPixel(int x, int y, ColorChannel channel, double val);
	
	public abstract void setPixel(int x, int y, int rgb);

	public abstract int getPixel(int x, int y);

	public abstract ImageType getType();
	
	@Override
	public abstract Object clone();
}
