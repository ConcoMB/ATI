package domain;

public interface Image {

	public static enum ColorChannel { RED, GREEN, BLUE }

	public static enum ImageType { RGB, GREYSCALE }

	public static enum ImageFormat { BMP, PGM, PPM, RAW }

	public static final int GREY_MAX = 256;

	public void setPixel(int x, int y, ColorChannel channel, double color);

	public void setRGBPixel(int x, int y, int rgb);

	public int getRGBPixel(int x, int y);

	public int getHeight();

	public int getWidth();

	public ImageType getType();

	public ImageFormat getImageFormat();
}
