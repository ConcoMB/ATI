package domain.tracking;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import domain.Image;
import domain.Image.ColorChannel;

public class Frontier {

	private Set<Point> outer;
	private Set<Point> inner;
	private Set<Point> innerBorder;
	private Set<Point> outerBorder;
	private Image image;
	
	public Frontier(Image image, Set<Point> outer, Set<Point> outerBorder, Set<Point> innerBorder, Set<Point> inner) {
		this.image = image;
		this.outer = outer;
		this.inner = inner;
		this.innerBorder = innerBorder;
		this.outerBorder = outerBorder;
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
//			throw new IllegalArgumentException();
			return;
		}
		innerBorder.add(p);
		for(Point n : n8(p)) {
			if (outer.contains(n)) {
				outerBorder.add(n);
				outer.remove(n);
			}
		}
		for(Point n : n8(p)) {
			if (innerBorder.contains(n)) {
				boolean reallyInnerBorder = false;
				for(Point nn : n8(n)) {
					if (outerBorder.contains(nn)) {
						reallyInnerBorder = true;
						break;
					}
				}
				if (!reallyInnerBorder) {
					inner.add(n);
					innerBorder.remove(n);
				}
			}
		}
	}

	public void contract(Point p) {
		if (!innerBorder.remove(p)) {
//			throw new IllegalArgumentException();
			return;
		}
		outerBorder.add(p);
		for(Point n : n8(p)) {
			if (inner.contains(n)) {
				innerBorder.add(n);
				inner.remove(n);
			}
		}
		for(Point n : n8(p)) {
			if (outerBorder.contains(n)) {
				boolean reallOuterBorder = false;
				for(Point nn : n8(n)) {
					if (innerBorder.contains(nn)) {
						reallOuterBorder = true;
						break;
					}
				}
				if (!reallOuterBorder) {
					outer.add(n);
					outerBorder.remove(n);
				}
			}
		}
	}
	
	private Set<Point> n8(Point p) {
		Set<Point> n8 = new HashSet<Point>();
		if (p.x > 0) {
			n8.add(new Point(p.x - 1, p.y));
			if (p.y > 0) {
				n8.add(new Point(p.x - 1, p.y - 1));
			}
			if (p.y < image.getHeight() -1) {
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
	
	public double averageInner(ColorChannel channel) {
		return average(inner, channel);
	}
	
	public double averageOuter(ColorChannel channel) {
		return average(outer, channel);
	}
	
	private double average(Set<Point> points, ColorChannel channel) {
		double avg = 0;
		for(Point p : points) {
			avg += image.getPixel(p.x, p.y, channel);  
		}
		return avg / points.size();
	}

	public Image getImage() {
		return image;
	}
	
}
