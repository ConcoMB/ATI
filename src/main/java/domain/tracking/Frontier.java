package domain.tracking;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import domain.Image;
import domain.Image.ColorChannel;
import static domain.Image.ColorChannel.*;
import static domain.tracking.Frontier.Side.*;

public class Frontier {

	public enum Side {
		INNER, OUTER
	};

	private Set<Point> outer = new HashSet<Point>();
	private Set<Point> inner = new HashSet<Point>();
	private Set<Point> innerBorder = new HashSet<Point>();
	private Set<Point> outerBorder = new HashSet<Point>();
	private Image image;
	// Arrays x color: 0 -> RED, 1 -> GREEN, 2 -> BLUE
	private double[] innerSum;
	private double[] outerSum;

	public Frontier(Point p, Point q, Image image) {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				Point r = new Point(x, y);
				if (x < p.x || x > q.x || y < p.y || y > q.y) {
					outer.add(r);
				} else if ((between(y, p.y, q.y) && (x == p.x || x == q.x))
						|| (between(x, p.x, q.x) && (y == p.y || y == q.y))) {
					outerBorder.add(r);
				} else if ((between(y, p.y + 1, q.y - 1) && (x == p.x + 1 || x == q.x - 1))
						|| (between(x, p.x + 1, q.x - 1) && (y == p.y + 1 || y == q.y - 1))) {
					innerBorder.add(r);
				} else {
					inner.add(r);
				}
			}
		}
		setImage(image);
		init();
	}

	public int tita(Point p) {
		if (outer.contains(p)) {
			return 3;
		}
		if (outerBorder.contains(p)) {
			return 1;
		}
		if (innerBorder.contains(p)) {
			return -1;
		}
		if (inner.contains(p)) {
			return -3;
		}
		throw new IllegalStateException("=(");
	}

	public Set<Point> getOuter() {
		return outer;
	}

	public Set<Point> getInner() {
		return inner;
	}

	public Set<Point> getInnerBorder() {
		return innerBorder;
	}

	public Set<Point> getOuterBorder() {
		return outerBorder;
	}

	public void expand(Point p) {
		if (!outerBorder.remove(p)) {
			// throw new IllegalArgumentException();
			return;
		}
		innerBorder.add(p);
		for (Point n : n8(p)) {
			if (outer.contains(n)) {
				outerBorder.add(n);
				removeFromOuterAndRecalculate(n);
			}
		}
		for (Point n : n8(p)) {
			if (innerBorder.contains(n)) {
				boolean reallyInnerBorder = false;
				for (Point nn : n8(n)) {
					if (outerBorder.contains(nn)) {
						reallyInnerBorder = true;
						break;
					}
				}
				if (!reallyInnerBorder) {
					addToInnerAndRecalculate(n);
					innerBorder.remove(n);
				}
			}
		}
	}

	public void contract(Point p) {
		if (!innerBorder.remove(p)) {
			// throw new IllegalArgumentException();
			return;
		}
		outerBorder.add(p);
		for (Point n : n8(p)) {
			if (inner.contains(n)) {
				innerBorder.add(n);
				removeFromInnerAndRecalculate(n);
			}
		}
		for (Point n : n8(p)) {
			if (outerBorder.contains(n)) {
				boolean realOuterBorder = false;
				for (Point nn : n8(n)) {
					if (innerBorder.contains(nn)) {
						realOuterBorder = true;
						break;
					}
				}
				if (!realOuterBorder) {
					addToOuterAndRecalculate(n);
					outerBorder.remove(n);
				}
			}
		}
	}

	public double averageInner(ColorChannel channel) {
		return innerSum[getAvgIndex(channel)] / inner.size();
	}

	public double averageOuter(ColorChannel channel) {
		return outerSum[getAvgIndex(channel)] / outer.size();
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	private void removeFromOuterAndRecalculate(Point p) {
		if (outer.remove(p)) {
			outerSum[0] -= image.getPixel(p, RED);
			outerSum[1] -= image.getPixel(p, GREEN);
			outerSum[2] -= image.getPixel(p, BLUE);
		}
	}

	private void removeFromInnerAndRecalculate(Point p) {
		if (inner.remove(p)) {
			innerSum[0] -= image.getPixel(p, RED);
			innerSum[1] -= image.getPixel(p, GREEN);
			innerSum[2] -= image.getPixel(p, BLUE);
		}
	}

	private void addToOuterAndRecalculate(Point p) {
		if (outer.add(p)) {
			outerSum[0] += image.getPixel(p, RED);
			outerSum[1] += image.getPixel(p, GREEN);
			outerSum[2] += image.getPixel(p, BLUE);
		}
	}

	private void addToInnerAndRecalculate(Point p) {
		if (inner.add(p)) {
			innerSum[0] += image.getPixel(p, RED);
			innerSum[1] += image.getPixel(p, GREEN);
			innerSum[2] += image.getPixel(p, BLUE);
		}
	}

	private Set<Point> n8(Point p) {
		Set<Point> n8 = new HashSet<Point>();
		if (p.x > 0) {
			n8.add(new Point(p.x - 1, p.y));
			if (p.y > 0) {
				n8.add(new Point(p.x - 1, p.y - 1));
			}
			if (p.y < image.getHeight() - 1) {
				n8.add(new Point(p.x - 1, p.y + 1));
			}
		}
		if (p.x < image.getWidth() - 1) {
			n8.add(new Point(p.x + 1, p.y));
			if (p.y > 0) {
				n8.add(new Point(p.x + 1, p.y - 1));
			}
			if (p.y < image.getHeight() - 1) {
				n8.add(new Point(p.x + 1, p.y + 1));
			}
		}
		if (p.y > 0) {
			n8.add(new Point(p.x, p.y - 1));
		}
		if (p.y < image.getHeight() - 1) {
			n8.add(new Point(p.x, p.y + 1));
		}
		return n8;
	}
	
	

	private int getAvgIndex(ColorChannel c) {
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

	private double sum(ColorChannel channel, Side side) {
		double sum = 0;
		Set<Point> points = side == INNER ? inner : outer;
		for (Point p : points) {
			sum += image.getPixel(p, channel);
		}
		return sum;
	}

	private boolean between(int m, int a, int b) {
		int min = Math.min(a, b);
		int max = Math.max(a, b);
		return m >= min && m <= max;
	}

	private void init() {
		innerSum = new double[3];
		outerSum = new double[3];
		innerSum[0] = sum(RED, INNER);
		innerSum[1] = sum(GREEN, INNER);
		innerSum[2] = sum(BLUE, INNER);
		outerSum[0] = sum(RED, OUTER);
		outerSum[1] = sum(GREEN, OUTER);
		outerSum[2] = sum(BLUE, OUTER);
	}
}
