package application.utils;

import static domain.Image.ColorChannel.BLUE;
import static domain.Image.ColorChannel.GREEN;
import static domain.Image.ColorChannel.RED;
import gui.Panel;

import java.awt.Point;
import java.util.HashSet;

import domain.Image;
import domain.mask.MaskFactory;
import domain.tracking.Frontier;

public class TrackingUtils {

	public static Image track(Frontier frontier, Panel panel) {
		Image image = (Image) frontier.getImage();
		MaskUtils.applyMask(image, MaskFactory.buildGaussianMask(5, 5));
		int i = 0;
		int Na = Math.max(image.getHeight(), image.getWidth());
		boolean changed = true;
		while (i < Na && changed) {
			System.out.println(i);
			changed = handleExpand(frontier);
			System.out.println("lOut");
			changed = handleContract(frontier) || changed;
			System.out.println("lIn");
			panel.loadImage(drawBorder(frontier, (Image)frontier.getImage().clone()));
			panel.paintImmediately(0, 0, image.getWidth(), image.getWidth());
			System.out.println("draw");
			i++;
		}
		return image;
	}

	private static Image  drawBorder(Frontier frontier, Image image) {
		for (Point p : frontier.getInnerBorder()) {
			image.setPixel(p.x, p.y, RED, 255);
			image.setPixel(p.x, p.y, GREEN, 0);
			image.setPixel(p.x, p.y, BLUE, 0);
		}
		for (Point p : frontier.getOuterBorder()) {
			image.setPixel(p.x, p.y, RED, 0);
			image.setPixel(p.x, p.y, GREEN, 255);
			image.setPixel(p.x, p.y, BLUE, 0);
		}
		return image;
	}

	private static boolean handleExpand(Frontier frontier) {
		boolean changed = false;
		for (Point p : new HashSet<Point>(frontier.getOuterBorder())) {
			if (velocity(frontier, p) > 0) {
				frontier.expand(p);
				changed = true;
			}
		}
		return changed;
	}

	private static boolean handleContract(Frontier frontier) {
		boolean changed = false;
		for (Point p : new HashSet<Point>(frontier.getInnerBorder())) {
			if (velocity(frontier, p) < 0) {
				frontier.contract(p);
				changed = true;
			}
		}
		return changed;
	}

	private static double velocity(Frontier f, Point p) {
		double red = f.getImage().getPixel(p.x, p.y, RED);
		double green = f.getImage().getPixel(p.x, p.y, GREEN);
		double blue = f.getImage().getPixel(p.x, p.y, BLUE);

		double p1 = Math.sqrt(Math.pow((f.averageInner(RED) - red), 2)
				+ Math.pow((f.averageInner(GREEN) - green), 2)
				+ Math.pow((f.averageInner(BLUE) - blue), 2));
		double p2 = Math.sqrt(Math.pow((f.averageOuter(RED) - red), 2)
				+ Math.pow((f.averageOuter(GREEN) - green), 2)
				+ Math.pow((f.averageOuter(BLUE) - blue), 2));
		return p2 - p1;
	}
}
