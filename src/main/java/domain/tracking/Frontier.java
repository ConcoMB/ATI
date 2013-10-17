package domain.tracking;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import application.utils.MaskUtils;
import domain.Image;
import domain.mask.Mask;
import domain.mask.MaskFactory;

public abstract class Frontier {

	public enum Side {
		INNER, OUTER
	};

	protected Set<Point> innerBorder = new HashSet<Point>();
	protected Set<Point> outerBorder = new HashSet<Point>();
	protected Tita tita;
	private int w, h;
	private Mask mask = MaskFactory.buildGaussianMask(5, 10);

	protected Frontier(Point p, Point q, Image image) {
		tita = new Tita(image);
		w = image.getWidth();
		h = image.getHeight();
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				Point r = new Point(x, y);
				if (x < p.x || x > q.x || y < p.y || y > q.y) {
					tita.setValue(r, 3);
				} else if ((between(y, p.y, q.y) && (x == p.x || x == q.x))
						|| (between(x, p.x, q.x) && (y == p.y || y == q.y))) {
					outerBorder.add(r);
					tita.setValue(r, 1);
				} else if ((between(y, p.y + 1, q.y - 1) && (x == p.x + 1 || x == q.x - 1))
						|| (between(x, p.x + 1, q.x - 1) && (y == p.y + 1 || y == q.y - 1))) {
					innerBorder.add(r);
					tita.setValue(r, -1);
				} else {
					tita.setValue(r, -3);
				}
			}
		}
	}

	public Set<Point> getInnerBorder() {
		return innerBorder;
	}

	public Set<Point> getOuterBorder() {
		return outerBorder;
	}

	private void addToOuterBorder(Point p) {
		outerBorder.add(p);
		tita.setValue(p, 1);
	}

	private void addToInnerBorder(Point p) {
		innerBorder.add(p);
		tita.setValue(p, -1);
	}

	private void removeFromInnerBorder(Point p) {
		innerBorder.remove(p);
		tita.setValue(p, -3);
	}

	private void removeFromOuterBorder(Point p) {
		outerBorder.remove(p);
		tita.setValue(p, 3);
	}

	public void switch_in(Point p) {
		if (!outerBorder.remove(p)) {
			return;
		}
		innerBorder.add(p);
		tita.setValue(p, -1);
		for (Point n : n4(p)) {
			if (tita.getValue(n) == 3) {
				addToOuterBorder(n);
			}
		}
	}

	public void switch_out(Point p) {
		if (!innerBorder.remove(p)) {
			return;
		}
		outerBorder.add(p);
		tita.setValue(p, 1);
		for (Point n : n4(p)) {
			if (tita.getValue(n) == -3) {
				addToInnerBorder(n);
			}
		}
	}


	public Image getImage() {
		return tita.getImage();
	}

	public abstract void setImage(Image image);

	private Set<Point> n4(Point p) {
		Set<Point> n4 = new HashSet<Point>();
		if (p.x > 0) {
			n4.add(new Point(p.x - 1, p.y));
		}
		if (p.x < w - 1) {
			n4.add(new Point(p.x + 1, p.y));
		}
		if (p.y > 0) {
			n4.add(new Point(p.x, p.y - 1));
		}
		if (p.x < h - 1) {
			n4.add(new Point(p.x, p.y + 1));
		}
		return n4;
	}

	private boolean between(int m, int a, int b) {
		int min = Math.min(a, b);
		int max = Math.max(a, b);
		return m >= min && m <= max;
	}

	public int process(Function f) {
		int changed = 0;
		for (Point p : new HashSet<Point>(outerBorder)) {
			if (f.val(p) > 0) {
				switch_in(p);
				changed++;
			}
		}
		for (Point p : new HashSet<Point>(innerBorder)) {
			boolean notFound = true;
			for (Point n : n4(p)) {
				if (tita.getValue(n) >= 0) {
					notFound = false;
					break;
				}
			}
			if (notFound) {
				removeFromInnerBorder(p);
			}
		}
		for (Point p : new HashSet<Point>(innerBorder)) {
			if (f.val(p) < 0) {
				switch_out(p);
				changed++;
			}
		}
		for (Point p : new HashSet<Point>(outerBorder)) {
			boolean notFound = true;
			for (Point n : n4(p)) {
				if (tita.getValue(n) <= 0) {
					notFound = false;
					break;
				}
			}
			if (notFound) {
				removeFromOuterBorder(p);
			}
		}
		return changed;
	}

	public boolean change() {
		long t0 = System.currentTimeMillis();
		int changed = process(new VelocityFunction());
		long t1 = System.currentTimeMillis();
		process(new TitaFunction());;
//		System.out.println("tardo " + (System.currentTimeMillis() - t1) + " y " + (t1-t0));
		return changed > 10;
	}

	private interface Function {
		public double val(Point p);
	}
	
	private class TitaFunction implements Function {
		
		double[][] convolution;
		
		public TitaFunction() {
			convolution = MaskUtils.applyMask(tita, innerBorder, outerBorder, mask);
		}
		
		@Override
		public double val(Point p) {
			return -convolution[p.x][p.y];
		}
	}
	
	private class VelocityFunction implements Function {
		
		@Override
		public double val(Point p) {
			return tita.velocity(p);
		}
	}
}
