package application;

import domain.Image;
import domain.Image.ColorChannel;
import static domain.Image.ColorChannel.*;

public class NoiseUtils {

	public static Image saltAndPepper(Image original, double minP, double maxP) {
		if (original == null) {
			return null;
		}
		Image saltAndPepper = new Image(original.getWidth(), original.getHeight(),
				original.getImageFormat(), original.getType());
		for (int x = 0; x < original.getWidth(); x++) {
			for (int y = 0; y < original.getHeight(); y++) {
				double p = Math.random();
				setPixelSaltAndPepper(original, saltAndPepper, minP, maxP, p, RED, x, y);
				setPixelSaltAndPepper(original, saltAndPepper, minP, maxP, p, GREEN, x, y);
				setPixelSaltAndPepper(original, saltAndPepper, minP, maxP, p, BLUE, x, y);
			}
		}
		return saltAndPepper;
	}
	
	private static void setPixelSaltAndPepper(Image original,
			Image saltAndPepper, double minP, double maxP, double p,
			ColorChannel color, int x, int y) {
		double pix;
		if (p < minP) {
			pix =  0;
		} else if (p > maxP) {
			pix = Image.MAX_VAL;
		} else {
			pix = original.getPixel(x, y, color);
		}
		saltAndPepper.setPixel(x, y, color, pix);
	}
	
	public static Image gaussianNoise(Image original, double avg, double dev, double p) {
		if (original == null) {
			return null;
		}
		Image gaussian = new Image(original.getWidth(), original.getHeight(),
				original.getImageFormat(), original.getType());
		for (int x = 0; x < original.getWidth(); x++) {
			for (int y = 0; y < original.getHeight(); y++) {
				double rand = Math.random();
				if(rand < p){
					gaussian.setPixel(x, y, RED, original.getPixel(x, y, RED) + gaussianGenerator(avg, dev));
					gaussian.setPixel(x, y, GREEN, original.getPixel(x, y, GREEN) + gaussianGenerator(avg, dev));
					gaussian.setPixel(x, y, BLUE, original.getPixel(x, y, BLUE) + gaussianGenerator(avg, dev));
				} else {
					gaussian.setPixel(x, y, original.getPixel(x, y));
				}
			}
		}
		return gaussian;
	}
	
	private static double gaussianGenerator(double avg, double dev) {
		return avg+dev*Math.sqrt(-2*Math.log(Math.random()))*Math.cos(2*Math.PI*Math.random());
	}
	
	public static Image exponentialNoise(Image original, double lambda, double p) {
		if (original == null) {
			return null;
		}
		Image gaussian = new Image(original.getWidth(), original.getHeight(),
				original.getImageFormat(), original.getType());
		for (int x = 0; x < original.getWidth(); x++) {
			for (int y = 0; y < original.getHeight(); y++) {
				double rand = Math.random();
				if(rand < p){
					gaussian.setPixel(x, y, RED, original.getPixel(x, y, RED) * expGenerator(lambda));
					gaussian.setPixel(x, y, GREEN, original.getPixel(x, y, GREEN) * expGenerator(lambda));
					gaussian.setPixel(x, y, BLUE, original.getPixel(x, y, BLUE) * expGenerator(lambda));
				} else {
					gaussian.setPixel(x, y, original.getPixel(x, y));
				}
			}
		}
		return gaussian;
	}
	
	private static double expGenerator(double lambda) {
		return -Math.log(Math.random())/lambda;
	}
	
	public static Image rayleighNoise(Image original, double psi, double p) {
		if (original == null) {
			return null;
		}
		Image gaussian = new Image(original.getWidth(), original.getHeight(),
				original.getImageFormat(), original.getType());
		for (int x = 0; x < original.getWidth(); x++) {
			for (int y = 0; y < original.getHeight(); y++) {
				double rand = Math.random();
				if(rand < p){
					gaussian.setPixel(x, y, RED, original.getPixel(x, y, RED) * rayleighGenerator(psi));
					gaussian.setPixel(x, y, GREEN, original.getPixel(x, y, GREEN) * rayleighGenerator(psi));
					gaussian.setPixel(x, y, BLUE, original.getPixel(x, y, BLUE) * rayleighGenerator(psi));
				} else {
					gaussian.setPixel(x, y, original.getPixel(x, y));
				}
			}
		}
		return gaussian;
	}
	
	private static double rayleighGenerator(double psi) {
		return psi*Math.sqrt(-Math.log(1-Math.random()));
	}
}
