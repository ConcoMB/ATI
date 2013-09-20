package application.utils;

import static domain.Image.ColorChannel.BLUE;
import static domain.Image.ColorChannel.GREEN;
import static domain.Image.ColorChannel.RED;
import domain.Image;
import domain.Image.ColorChannel;

public class ThresholdUtils {

	public static Image threshold(Image original, int value) {
		if (original == null) {
			return null;
		}
		Image thresholded = (Image) original.clone();
		threshold(thresholded, value, RED);
		threshold(thresholded, value, GREEN);
		threshold(thresholded, value, BLUE);
		return thresholded;
	}

	public static void threshold(Image image, int value, ColorChannel c) {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				image.setPixel(x, y, c,
						image.getPixel(x, y, c) > value ? Image.MAX_VAL : 0);
			}
		}
	}

	public static Image globalThreshold(Image image, int T, int delta) {
		int globalThreshold = getGlobalThresholdValue(T, image, delta, RED);
		return threshold(image, globalThreshold);
	}

	public static void globalThreshold(Image image, ColorChannel c, int T,
			int delta) {
		int globalThreshold = getGlobalThresholdValue(T, image, delta, c);
		threshold(image, globalThreshold, c);
	}
	
	static int getGlobalThresholdValue(int T, Image img, int delta,
			ColorChannel c) {
		int currentT = T;
		int previousT = 0;
		int i = 0;
		do {
			previousT = currentT;
			currentT = getAdjustedThreshold(currentT, img, c);
			i++;
		} while (Math.abs((currentT - previousT)) >= delta);
//		currentT -= 30;
		System.out.println(c);
		System.out.println("Iteraciones: " + i);
		System.out.println("T: " + currentT);
		return currentT;
	}

	private static int getAdjustedThreshold(int previousThreshold, Image img,
			ColorChannel c) {
		int amountOfHigher = 0;
		int amountOfLower = 0;
		double sumOfHigher = 0;
		double sumOfLower = 0;

		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				double aPixel = img.getPixel(x, y, c);
				if (aPixel >= previousThreshold) {
					amountOfHigher++;
					sumOfHigher += (int)aPixel;
				} else {
					amountOfLower++;
					sumOfLower += (int)aPixel;
				}
			}
		}
		double m1 = sumOfHigher / amountOfHigher;
		double m2 = sumOfLower / amountOfLower;
		return (int) ((m1 + m2) / 2);
	}

	public static Image otsuThreshold(Image img) {
		Image thresholded = (Image) img.clone();
		otsuThreshold(thresholded, RED);
		otsuThreshold(thresholded, GREEN);
		otsuThreshold(thresholded, BLUE);
		return thresholded;
	}

	public static void otsuThreshold(Image img, ColorChannel c) {
		double maxSigma = 0;
		int threshold = 0;
		double[] probabilities = getProbabilitiesOfColorLevel(img, c);
		for (int i = 0; i < Image.MAX_VAL; i++) {
			double aSigma = getSigma(i, probabilities);
			if (aSigma > maxSigma) {
				maxSigma = aSigma;
				threshold = i;
			}
		}
		System.out.println("Threshold " + c.toString() + ": " + threshold);
		threshold(img, threshold, c);
	}

	private static double getSigma(int threshold, double[] probabilities) {
		double w1 = 0;
		double w2 = 0;
		for (int i = 0; i < probabilities.length; i++) {
			if (i <= threshold) {
				w1 += probabilities[i];
			} else {
				w2 += probabilities[i];
			}
		}
		if (w1 == 0 || w2 == 0) {
			return 0;
		}
		double mu1 = 0;
		double mu2 = 0;
		for (int i = 0; i < probabilities.length; i++) {
			if (i <= threshold) {
				mu1 += i * probabilities[i] / w1;
			} else {
				mu2 += i * probabilities[i] / w2;
			}
		}
		double mu_t = mu1 * w1 + mu2 * w2;
		return w1 * Math.pow((mu1 - mu_t), 2) + w2
				* Math.pow((mu2 - mu_t), 2);
	}

	private static double[] getProbabilitiesOfColorLevel(Image img,
			ColorChannel c) {
		double[] probabilities = new double[Image.MAX_VAL + 1];

		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int aColorPixel = (int) img.getPixel(x, y, c);
				probabilities[aColorPixel]++;
			}
		}
		int size = (img.getWidth() * img.getHeight());
		for (int i = 0; i < probabilities.length; i++) {
			probabilities[i] /= size;
		}
		return probabilities;
	}

	public static void thresholdWithHisteresis(Image image, ColorChannel c,
			double t1, double t2) {
		int height = image.getHeight();
		int width = image.getWidth();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				double pixel = image.getPixel(x, y, c);
				double val = pixel;
				if (pixel < t1) {
					val = 0;
				} else if (pixel > t2) {
					// Correct pixels (Border pixels)
					val = Image.MAX_VAL;
				}
				image.setPixel(x, y, c, val);
			}
		}

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				double pixel = image.getPixel(x, y, c);
				if (pixel >= t1 && pixel <= t2) {
					// Analyze if the pixel is neighbour of a border (a correct
					// pixel)
					boolean isBorderNeighbor1 = y > 0
							&& image.getPixel(x, y - 1, c) == Image.MAX_VAL;
					boolean isBorderNeighbor2 = x > 0
							&& image.getPixel(x - 1, y, c) == Image.MAX_VAL;
					boolean isBorderNeighbor3 = y < height - 1
							&& image.getPixel(x, y + 1, c) == Image.MAX_VAL;
					boolean isBorderNeighbor4 = x < width - 1
							&& image.getPixel(x + 1, y, c) == Image.MAX_VAL;
					if (isBorderNeighbor1 || isBorderNeighbor2
							|| isBorderNeighbor3 || isBorderNeighbor4) {
						image.setPixel(x, y, Image.MAX_VAL);
					} else {
						image.setPixel(x, y, 0);
					}
				}
			}
		}
	}
}
