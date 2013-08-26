package domain;

import java.awt.Point;
import java.util.Iterator;
import java.util.TreeSet;

public class Channel implements Cloneable {

	static final int MIN_VAL = 0;
	static final int MAX_VAL = 255;

	private int width;
	private int height;

	// The matrix is represented by an array, and to get a pixel(x,y) make y *
	// this.getWidth() + x
	private double[] channel;

	public Channel(int width, int height) {
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException(
					"Images must have at least 1x1 pixel size");
		}
		this.width = width;
		this.height = height;
		this.channel = new double[width * height];
	}

	public boolean validPixel(int x, int y) {
		return y >= 0 && y < height && x >= 0 && x < width;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void setPixel(int x, int y, double val) {
		if (!validPixel(x, y)) {
			throw new IndexOutOfBoundsException();
		}
		channel[y * width + x] = val;
	}

	public double getPixel(int x, int y) {
		if (!validPixel(x, y)) {
			throw new IndexOutOfBoundsException();
		}
		return channel[y * width + x];
	}

	int truncatePixel(double originalValue) {
		if (originalValue > MAX_VAL) {
			return MAX_VAL;
		} else if (originalValue < MIN_VAL) {
			return MIN_VAL;
		}
		return (int) originalValue;
	}
	
	@Override
	public Channel clone() {
		Channel newChannel = new Channel(width, height);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				newChannel.setPixel(i, j, this.getPixel(i, j));
			}
		}
		return newChannel;
	}
	
	public void applyMedianMask(Point point) {
		Channel newChannel = new Channel(this.width, this.height);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				double newPixel = applyMedianPixelMask(x, y, point);
				newChannel.setPixel(x, y, newPixel);
			}
		}
		this.channel = newChannel.channel;
	}
	
	private double applyMedianPixelMask(int x, int y, Point point) {
		TreeSet<Double> pixelsAffected = new TreeSet<Double>();
		for (int i = -point.x / 2; i <= point.x / 2; i++) {
			for (int j = -point.y / 2; j <= point.y / 2; j++) {
				if (this.validPixel(x + i, y + j)) {
					double oldColor = this.getPixel(x + i, y + j);
					pixelsAffected.add(oldColor);
				}
			}
		}

		double valueToReturn = 0;
		int indexToReturn = pixelsAffected.size() / 2;
		Iterator<Double> iterator = pixelsAffected.iterator();
		for (int i = 0; iterator.hasNext(); i++) {
			double each = iterator.next();
			if (i == indexToReturn) {
				valueToReturn = each;
			}
		}
		return valueToReturn;
	}
}
