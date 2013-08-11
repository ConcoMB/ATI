package domain;

import static domain.Image.ColorChannel.BLUE;
import static domain.Image.ColorChannel.GREEN;
import static domain.Image.ColorChannel.RED;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Image implements Cloneable {

	public static enum ColorChannel {
		RED, GREEN, BLUE
	}

	public static enum ImageType {
		COLOR, GREYSCALE
	}

	public static enum ImageFormat {
		BMP, PGM, PPM, RAW
	}

	public static final int MAX_VAL = 256;

	private ImageType type;
	private ImageFormat format;
	private Channel red;
	private Channel green;
	private Channel blue;
	private BufferedImage bufferedImage;

	public Image(int height, int width, ImageFormat format, ImageType type) {
		this.red = new Channel(width, height);
		this.green = new Channel(width, height);
		this.blue = new Channel(width, height);

		this.format = format;
		this.type = type;
	}

	public Image(BufferedImage bi, ImageFormat format, ImageType type) {
		this(bi.getHeight(), bi.getWidth(), format, type);
		bufferedImage = bi;
		initRGB(bi);
	}

	public static Image reuse(Image img, BufferedImage newImg) {
		img.bufferedImage = newImg;
		img.initRGB(newImg);
		return img;
	}

	private void initRGB(BufferedImage bi) {
		int[] rgbData = bi.getRGB(0, 0, bi.getWidth(), bi.getHeight(), null, 0,
				bi.getWidth());

		int colorRed;
		int colorGreen;
		int colorBlue;
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

	public void setPixel(int x, int y, int rgb) {
		this.setPixel(x, y, RED, ColorUtils.getRedFromRGB(rgb));
		this.setPixel(x, y, GREEN, ColorUtils.getGreenFromRGB(rgb));
		this.setPixel(x, y, BLUE, ColorUtils.getBlueFromRGB(rgb));
	}

	public void setPixel(int x, int y, ColorChannel channel, double color) {
		if (!red.validPixel(x, y)) {
			throw new IllegalArgumentException("Invalid pixels on setPixel");
		}
		switch(channel) {
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

	public int getRGBPixel(int x, int y) {
		int redComponent = this.red.truncatePixel(getPixelFromChannel(x, y, RED));
		int greenComponent = this.green.truncatePixel(getPixelFromChannel(x, y, GREEN));
		int blueComponent = this.blue.truncatePixel(getPixelFromChannel(x, y, BLUE));
		return new Color(redComponent, greenComponent, blueComponent).getRGB();
	}

	public double getPixelFromChannel(int x, int y, ColorChannel channel) {
		switch(channel) {
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

	public int getHeight() {
		return red.getHeight();
	}

	public int getWidth() {
		return red.getWidth();
	}

	public ImageType getType() {
		return type;
	}

	public ImageFormat getImageFormat() {
		return format;
	}

	
	public BufferedImage getBufferedImage() {
		if (bufferedImage == null) {
			bufferedImage = new BufferedImage(getWidth(), getHeight(), 1);
		}
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				bufferedImage.setRGB(x, y, getRGBPixel(x, y));
			}
		}
		return bufferedImage;
	}
	
	@Override
	public Object clone() {
		BufferedImage bi = ColorUtils.populateEmptyBufferedImage(this);
		return new Image(bi, format, type);
	}
}
