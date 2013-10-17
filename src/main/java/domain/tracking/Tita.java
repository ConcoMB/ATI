package domain.tracking;

import static domain.Image.ChannelType.BLUE;
import static domain.Image.ChannelType.GREEN;
import static domain.Image.ChannelType.RED;

import java.awt.Point;

import domain.Image;
import domain.Image.ChannelType;
import domain.RgbImage;

public class Tita {

	double[][] values;
	private double[] innerSum = new double[3];
	private double[] outerSum = new double[3];
	private int innerSize = 0;
	private int outerSize = 0;
	private Image image;
	
	public Tita(Image image){
		this.image = image;
		values = new double[image.getWidth()][image.getHeight()];
	}
	
	public double getValue(Point p) {
		return getValue(p.x, p.y);
	}
	
	public double getValue(int x, int y) {
		return values[x][y];
	}

	public void setValue(int x, int y, int i) {
		if (values[x][y] == 3 && i != 3) {
			// lo saco del outer
			removeFromOuter(x, y);
		}
		if (values[x][y] == -3 && i != -3) {
			// lo saco del inner
			removeFromInner(x, y);
		}
		if (i == 3 && values[x][y] != 3) {
			// lo agrego al outer
			addToOuter(x, y);
		}
		if (i == -3 && values[x][y] != -3) {
			// lo agrego al inner
			addToInner(x, y);
		}
		values[x][y] = i;
	}
	
	public void setValue(Point p, int i) {
		setValue(p.x, p.y, i);
	}

	public int getWidth() {
		return values.length;
	}
	
	public int getHeight() {
		return values[0].length;
	}
	
	public double averageInner(ChannelType channel) {
		return innerSum[getAvgIndex(channel)] / innerSize;
	}

	public double averageOuter(ChannelType channel) {
		return outerSum[getAvgIndex(channel)] / outerSize;
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

	
	protected void removeFromOuter(int x, int y) {
		outerSum[0] -= image.getPixel(x, y, RED);
		outerSum[1] -= image.getPixel(x, y, GREEN);
		outerSum[2] -= image.getPixel(x, y, BLUE);
		outerSize--;
	}

	protected void removeFromInner(int x, int y) {
		innerSum[0] -= image.getPixel(x, y, RED);
		innerSum[1] -= image.getPixel(x, y, GREEN);
		innerSum[2] -= image.getPixel(x, y, BLUE);
		innerSize--;
	}

	protected void addToOuter(int x, int y) {
		outerSum[0] += image.getPixel(x, y, RED);
		outerSum[1] += image.getPixel(x, y, GREEN);
		outerSum[2] += image.getPixel(x, y, BLUE);
		outerSize++;
	}

	protected void addToInner(int x, int y) {
		innerSum[0] += image.getPixel(x, y, RED);
		innerSum[1] += image.getPixel(x, y, GREEN);
		innerSum[2] += image.getPixel(x, y, BLUE);
		innerSize++;
	}
	
	public double velocity(Point p) {
		double p1, p2;
		double red = image.getPixel(p.x, p.y, RED);
		double green = image.getPixel(p.x, p.y, GREEN);
		double blue = image.getPixel(p.x, p.y, BLUE);

		p1 = Math.sqrt(Math.pow((averageInner(RED) - red), 2)
				+ Math.pow((averageInner(GREEN) - green), 2)
				+ Math.pow((averageInner(BLUE) - blue), 2));
		p2 = Math.sqrt(Math.pow((averageOuter(RED) - red), 2)
				+ Math.pow((averageOuter(GREEN) - green), 2)
				+ Math.pow((averageOuter(BLUE) - blue), 2));

		return p2 - p1;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
		
	}
}
