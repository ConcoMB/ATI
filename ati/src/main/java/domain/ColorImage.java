package domain;

import static domain.Image.ColorChannel.BLUE;
import static domain.Image.ColorChannel.GREEN;
import static domain.Image.ColorChannel.RED;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ColorImage extends Image {

	private Channel red;
	private Channel green;
	private Channel blue;

	public ColorImage(BufferedImage bi, ImageFormat format) {
		this(bi.getHeight(), bi.getWidth(), format);
        setBufferedImage(bi);
        initRgb(bi);
	}
	
	public ColorImage(int height, int width, ImageFormat format) {
		this.red = new Channel(width, height);
		this.green = new Channel(width, height);
		this.blue = new Channel(width, height);
		setHeight(height);
		setWidth(width);
		setFormat(format);
	}

	public void setPixel(int x, int y, ColorChannel channel, double color) {
		if (!red.validPixel(x, y)) {
			throw new IllegalArgumentException("Invalid pixels on setPixel");
		}
		switch (channel) {
		case RED:
			red.setPixel(x, y, color);
			return;
		case GREEN:
			green.setPixel(x, y, color);
			return;
		case BLUE:
			blue.setPixel(x, y, color);
			return;
		default:
			throw new IllegalStateException();
		}
	}

	@Override
	public void setPixel(int x, int y, int rgb) {
		this.setPixel(x, y, RED, ColorUtils.getRedFromRGB(rgb));
		this.setPixel(x, y, GREEN, ColorUtils.getGreenFromRGB(rgb));
		this.setPixel(x, y, BLUE, ColorUtils.getBlueFromRGB(rgb));
	}
	
	public ImageType getType() {
		return Image.ImageType.COLOR;
	}

	@Override
	public int getPixel(int x, int y) {
		int redColor = red.truncatePixel(getPixelFromChannel(x, y, RED));
		int greenColor = green.truncatePixel(getPixelFromChannel(x, y, GREEN));
		int blueColor = blue.truncatePixel(getPixelFromChannel(x, y, BLUE));
		return new Color(redColor, greenColor, blueColor).getRGB();
	}
	
	@Override
	public ColorImage clone() {
        BufferedImage bi = ColorUtils.populateEmptyBufferedImage(this);
		return new ColorImage(bi, getFormat());
	}

	private double getPixelFromChannel(int x, int y, ColorChannel channel) {
		switch (channel) {
		case RED:
			return red.getPixel(x, y);
		case GREEN:
			return green.getPixel(x, y);
		case BLUE:
			return blue.getPixel(x, y);
		default:
			throw new IllegalStateException();
		}
	}
	
	private void initRgb(BufferedImage bi) {
        int[] rgbData = bi.getRGB(0,0, bi.getWidth(), bi.getHeight(),
                null, 0,bi.getWidth());
        int colorRed, colorGreen, colorBlue;
        for (int x = 0; x < bi.getWidth(); x++) {
            for (int y = 0; y < bi.getHeight(); y++) {
                colorRed = (rgbData[(y * bi.getWidth()) + x] >> 16) & 0xFF;
                colorGreen = (rgbData[(y * bi.getWidth()) + x] >> 8) & 0xFF;
                colorBlue = (rgbData[(y * bi.getWidth()) + x]) & 0xFF;
                red.setPixel(x, y, colorRed);
                green.setPixel(x, y, colorGreen);
                blue.setPixel(x, y, colorBlue);
            }
        }
    }
}
