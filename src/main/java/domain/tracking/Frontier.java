package domain.tracking;

import static domain.tracking.Frontier.Side.INNER;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import domain.Image;
import domain.Image.ChannelType;

public abstract class Frontier {

	public enum Side {
		INNER, OUTER
	};

	protected Set<Point> outer = new HashSet<Point>();
	protected Set<Point> inner = new HashSet<Point>();
	protected Set<Point> innerBorder = new HashSet<Point>();
	protected Set<Point> outerBorder = new HashSet<Point>();
	protected Image image;

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
		for (Point n : neighbors(p)) {
			if (outer.contains(n)) {
				outerBorder.add(n);
				removeFromOuterAndRecalculate(n);
			}
		}
		for (Point n : neighbors(p)) {
			if (innerBorder.contains(n)) {
				boolean reallyInnerBorder = false;
				for (Point nn : neighbors(n)) {
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
		for (Point n : neighbors(p)) {
			if (inner.contains(n)) {
				innerBorder.add(n);
				removeFromInnerAndRecalculate(n);
			}
		}
		for (Point n : neighbors(p)) {
			if (outerBorder.contains(n)) {
				boolean realOuterBorder = false;
				for (Point nn : neighbors(n)) {
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

	public abstract double averageInner(ChannelType channel);

	public abstract double averageOuter(ChannelType channel);

	public Image getImage() {
		return image;
	}

	public abstract void setImage(Image image);

	private Set<Point> neighbors(Point p) {
		Set<Point> neighbors = new HashSet<Point>();
		for (int i = -2; i <= 2; i++) {
			for (int j = -2; j <= 2; j++) {
				addIfExists(neighbors, p.x + i, p.y + j);
			}
		}
		return neighbors;
	}

	private void addIfExists(Set<Point> neighbors, int x, int y) {
		if (x >= 0 && x < image.getWidth() && y >=0 && y < image.getHeight()) {
			neighbors.add(new Point(x, y));
		}
	}

	protected double sum(ChannelType channel, Side side) {
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
	
	protected abstract void removeFromOuterAndRecalculate(Point p);
	protected abstract void removeFromInnerAndRecalculate(Point p);
	protected abstract void addToOuterAndRecalculate(Point p);
	protected abstract void addToInnerAndRecalculate(Point p);


}
