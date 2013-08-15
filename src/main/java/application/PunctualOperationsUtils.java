package application;

import domain.Image;
import static domain.Image.ColorChannel.*;

public class PunctualOperationsUtils {

	public static Image createInverse(Image original) {
		if (original == null) {
			return null;
		}
		Image inverse = new Image(original.getHeight(), original.getWidth(), original.getImageFormat(), original.getType());
		for (int x = 0; x < original.getHeight(); x++) {
			for (int y = 0; y < original.getWidth(); y++) {
				inverse.setPixel(x, y, Image.MAX_VAL - original.getPixel(x, y));
			}
		}
		return inverse;
	}

	public static Image threshold(Image original, int value) {
		if (original == null) {
			return null;
		}
		Image threshold = new Image(original.getHeight(), original.getWidth(), original.getImageFormat(), original.getType());
		for (int x = 0; x < original.getHeight(); x++) {
			for (int y = 0; y < original.getWidth(); y++) {
				int red = original.getPixel(x, y, RED) > value ? Image.MAX_VAL : 0;
				int green = original.getPixel(x, y, GREEN) > value ? Image.MAX_VAL : 0;
				int blue = original.getPixel(x, y, BLUE) > value ? Image.MAX_VAL : 0;
				threshold.setPixel(x, y, RED, red);
				threshold.setPixel(x, y, GREEN, green);
				threshold.setPixel(x, y, BLUE, blue);
			}
		}
		return threshold;
	}

	public static Image add(Image img1, Image img2) {
		if (img1 == null || img2 == null) {
			return null;
		}
		Image img = new Image(img1.getHeight(), img1.getWidth(), img1.getImageFormat(), img1.getType());
		double min = 0, max = 255;
		for (int x = 0; x < img1.getHeight(); x++) {
			for (int y = 0; y < img1.getWidth(); y++) {
				double red = img1.getPixel(x, y, RED) + img2.getPixel(x, y, RED);
				double green = img1.getPixel(x, y, GREEN) + img2.getPixel(x, y, GREEN);
				double blue = img1.getPixel(x, y, BLUE) + img2.getPixel(x, y, BLUE);
				min = min(red, green, blue, min);
				max = max(red, green, blue, max);
				img.setPixel(x, y, RED, red);
				img.setPixel(x, y, GREEN, green);
				img.setPixel(x, y, BLUE, blue);
			}
		}
		normalize(img, min, max);
		return img;
	}
	
	public static Image substract(Image img1, Image img2) {
		if (img1 == null || img2 == null) {
			return null;
		}
		Image img = new Image(img1.getHeight(), img1.getWidth(), img1.getImageFormat(), img1.getType());
		double min = 0, max = 255;
		for (int x = 0; x < img1.getHeight(); x++) {
			for (int y = 0; y < img1.getWidth(); y++) {
				double red = img1.getPixel(x, y, RED) - img2.getPixel(x, y, RED);
				double green = img1.getPixel(x, y, GREEN) - img2.getPixel(x, y, GREEN);
				double blue = img1.getPixel(x, y, BLUE) - img2.getPixel(x, y, BLUE);
				min = min(red, green, blue, min);
				max = max(red, green, blue, max);
				img.setPixel(x, y, RED, red);
				img.setPixel(x, y, GREEN, green);
				img.setPixel(x, y, BLUE, blue);
			}
		}
		normalize(img, min, max);
		return img;
	}
	
	public static Image multiply(Image img1, Image img2) {
		if (img1 == null || img2 == null) {
			return null;
		}
		Image img = new Image(img1.getHeight(), img1.getWidth(), img1.getImageFormat(), img1.getType());
		double min = 0, max = 255;
		for (int x = 0; x < img1.getHeight(); x++) {
			for (int y = 0; y < img1.getWidth(); y++) {
				double red = img1.getPixel(x, y, RED) * img2.getPixel(x, y, RED);
				double green = img1.getPixel(x, y, GREEN) * img2.getPixel(x, y, GREEN);
				double blue = img1.getPixel(x, y, BLUE) * img2.getPixel(x, y, BLUE);
				min = min(red, green, blue, min);
				max = max(red, green, blue, max);
				img.setPixel(x, y, RED, red);
				img.setPixel(x, y, GREEN, green);
				img.setPixel(x, y, BLUE, blue);
			}
		}
		normalize(img, min, max);
		return img;				
	}
	
	private static double min(double a, double b, double c, double d) {
		return Math.min(a, Math.min(b, Math.min(c, d)));
	}
	
	private static double max(double a, double b, double c, double d) {
		return Math.max(a, Math.max(b, Math.max(c, d)));
	}

	private static void normalize(Image image, double min, double max) {
		if (min == 0 && max == 255) {
			return;
		}
		double dif = max - min;
		for (int x = 0; x < image.getHeight(); x++) {
			for (int y = 0; y < image.getWidth(); y++) {
				double red = (image.getPixel(x, y, RED) - min) / dif;
				double green = (image.getPixel(x, y, GREEN) - min) / dif;
				double blue = (image.getPixel(x, y, BLUE) - min) / dif;
				image.setPixel(x, y, RED, toBase256(red));
				image.setPixel(x, y, GREEN, toBase256(green));
				image.setPixel(x, y, BLUE, toBase256(blue));
			}
		}
	}

	private static double toBase256(double val) {
		return Math.floor(val*255 + 0.5);
	}
}
