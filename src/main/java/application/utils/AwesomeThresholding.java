package application.utils;

import static domain.Image.ChannelType.BLUE;
import static domain.Image.ChannelType.GREEN;
import static domain.Image.ChannelType.RED;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import domain.Image;

public class AwesomeThresholding {

	private List<Cluster> clusters;
	private Image original, thresholded;

	public AwesomeThresholding(Image original) {
		this.original = original;
		this.thresholded = original.clone();
		ThresholdUtils.otsu(thresholded, RED);
		ThresholdUtils.otsu(thresholded, GREEN);
		ThresholdUtils.otsu(thresholded, BLUE);
		;
		clusters = new ArrayList<Cluster>(8);
		for (int i = 0; i < 8; i++) {
			clusters.add(new Cluster());
		}
		for (int x = 0; x < thresholded.getWidth(); x++) {
			for (int y = 0; y < thresholded.getHeight(); y++) {
				Point p = new Point(x, y);
				double r = thresholded.getPixel(p, RED);
				double g = thresholded.getPixel(p, GREEN);
				double b = thresholded.getPixel(p, BLUE);

				if (r == 0) {
					if (g == 0) {
						if (b == 0) {
							clusters.get(0).add(p);
							// ooo.add(p);
						} else {
							clusters.get(1).add(p);
							// ooi.add(p);
						}
					} else {
						if (b == 0) {
							clusters.get(2).add(p);
							// oio.add(p);
						} else {
							clusters.get(3).add(p);
							// oii.add(p);
						}
					}
				} else {
					if (g == 0) {
						if (b == 0) {
							clusters.get(4).add(p);
							// ioo.add(p);
						} else {
							clusters.get(5).add(p);
							// ioi.add(p);
						}
					} else {
						if (b == 0) {
							clusters.get(6).add(p);
							// iio.add(p);
						} else {
							clusters.get(7).add(p);
							// iii.add(p);
						}
					}
				}
			}
		}
		for (Cluster cluster : new ArrayList<Cluster>(clusters)) {
			if (cluster.points.isEmpty()) {
				clusters.remove(cluster);
			}
		}
		// System.out.println("Empezamos con");
		// for (Cluster cluster : clusters) {
		// System.out.println("Un cluster con " + cluster.points.size() +
		// " puntos");
		// }
	}

	public void calculateMeansAndDevs() {
		// System.out.println("Calculando medianas y desvios");
		for (Cluster cluster : clusters) {
			cluster.calculateMean();
			cluster.calculateDev();
		}
	}

	public void compareDevsAndMerge() {
		// System.out.println("Comparamos y vemos si mergeamos");
		boolean change = true;
		while (change) {
			change = false;
			for (int i = 0; i < clusters.size() && !change; i++) {
				for (int j = i + 1; j < clusters.size() && !change; j++) {
					Cluster c1 = clusters.get(i), c2 = clusters.get(j);
					double comparision = c1.compareDev(c2);
					// System.out.println("C1:"+c1.dev+"  C2:"+c2.dev+"  C3:"+comparision);
					if (comparision <= c1.dev || comparision <= c2.dev) {
						mergeClusters(c1, c2);
						 System.out.println("Mergeamos!");
						change = true;
					}
				}
			}
		}
	}

	private void mergeClusters(Cluster c1, Cluster c2) {
		System.out.println("Le merging");
		clusters.remove(c1);
		clusters.remove(c2);
		Cluster merged = new Cluster(c1, c2);
		clusters.add(merged);
	}

	private class Cluster {
		private List<Point> points;
		private Point3D mean;
		private double dev;

		public Cluster() {
			points = new ArrayList<Point>();
		}

		public Cluster(Cluster c1, Cluster c2) {
			points = new ArrayList<Point>();
			for (Point p : c1.points) {
				points.add(p);
			}
			for (Point p : c2.points) {
				points.add(p);
			}
			calculateMean();
			calculateDev();
		}

		private void add(Point p) {
			points.add(p);
		}

		public double compareDev(Cluster c2) {
			return Math.sqrt(Math.pow(mean.x - c2.mean.x, 2)
					+ Math.pow(mean.y - c2.mean.y, 2)
					+ Math.pow(mean.z - c2.mean.z, 2));
		}

		public void calculateDev() {
			dev = 0;
			for (Point p : points) {
				dev += Math.pow((original.getPixel(p, RED) - mean.x), 2)
						+ Math.pow((original.getPixel(p, GREEN) - mean.y), 2)
						+ Math.pow((original.getPixel(p, BLUE) - mean.z), 2);
			}
			dev = Math.sqrt(dev);
			dev /= points.size();
		}

		private void calculateMean() {
			double r, g, b;
			r = g = b = 0;
			for (Point p : points) {
				r += original.getPixel(p, RED);
				g += original.getPixel(p, GREEN);
				b += original.getPixel(p, BLUE);
			}
			r /= points.size();
			g /= points.size();
			b /= points.size();
			mean = new Point3D(r, g, b);
		}
	}

	public Image complete() {
		Image completed = original.shallowClone();
		for (Cluster cluster : clusters) {
			for (Point p : cluster.points) {
				completed.setPixel(p, RED, cluster.mean.x);
				completed.setPixel(p, GREEN, cluster.mean.y);
				completed.setPixel(p, BLUE, cluster.mean.z);
			}
		}
		return completed;
	}
}
