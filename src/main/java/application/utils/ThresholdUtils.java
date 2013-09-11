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
		Image threshold = new Image(original.getWidth(), original.getHeight(),
				original.getImageFormat(), original.getType());
		for (int x = 0; x < original.getWidth(); x++) {
			for (int y = 0; y < original.getHeight(); y++) {
				int red = original.getPixel(x, y, RED) > value ? Image.MAX_VAL
						: 0;
				int green = original.getPixel(x, y, GREEN) > value ? Image.MAX_VAL
						: 0;
				int blue = original.getPixel(x, y, BLUE) > value ? Image.MAX_VAL
						: 0;
				threshold.setPixel(x, y, RED, red);
				threshold.setPixel(x, y, GREEN, green);
				threshold.setPixel(x, y, BLUE, blue);
			}
		}
		return threshold;
	}
	
	public static Image globalThreshold(Image image, int T, int delta) {
		int globalThreshold = getGlobalThresholdValue(T, image, delta);
		return threshold(image, globalThreshold);
	}
	
	
	private static int getGlobalThresholdValue(int T, Image img, int delta) {
		// Step 1
		int currentT = T;
		int previousT = 0;

		// Step 5
		int i = 0;
		do {
			previousT = currentT;
			currentT = getAdjustedThreshold(currentT, img);
			i++;
		} while (Math.abs((currentT - previousT)) >= delta);
		System.out.println("Iteraciones: " + i);
		System.out.println("T: " + currentT);
		return currentT;
	}
	
	private static int getAdjustedThreshold(int previousThreshold, Image img) {
		double amountOfHigher = 0;
		double amountOfLower = 0;
		double sumOfHigher = 0;
		double sumOfLower = 0;

		// Step 3
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				for(ColorChannel c : Image.ColorChannel.values()) {
					double aPixel = img.getPixel(x, y, c);
					if (aPixel >= previousThreshold) {
						amountOfHigher += 1;
						sumOfHigher += aPixel;
					}
					if (aPixel < previousThreshold) {
						amountOfLower += 1;
						sumOfLower += aPixel;
					}
				}
			}
		}
		double m1 = (1 / amountOfHigher) * sumOfHigher;
		double m2 = (1 / amountOfLower) * sumOfLower;

		// Step 4
		return (int) (0.5 * (m1 + m2));
	}
	
	
	public static Image otsuThreshold(Image img) {
		double maxSigma = 0;
		int threshold = 0;
		double[] probabilities = getProbabilitiesOfEachColorLevel(img);
		for (int i = 0; i < Image.MAX_VAL; i++) {
			double aSigma = getSigma(i, probabilities);
			if (aSigma > maxSigma) {
				maxSigma = aSigma;
				threshold = i;
			}
		}
		System.out.println("Threshold: " + threshold);
		return threshold(img, threshold);
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
		double sigma_B = w1 * Math.pow((mu1 - mu_t), 2) + w2
				* Math.pow((mu2 - mu_t), 2);
		return sigma_B;
	}

	private static double[] getProbabilitiesOfEachColorLevel(Image img) {
		double[] probabilities = new double[Image.MAX_VAL + 1];

		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				for(ColorChannel c : ColorChannel.values()){
					int aColorPixel = (int) img.getPixel(x, y, c);
					probabilities[aColorPixel] += 1;
				}
			}
		}
		for (int i = 0; i < probabilities.length; i++) {
			probabilities[i] /= (img.getWidth() * img.getHeight());
		}

		return probabilities;
	}
	
}
