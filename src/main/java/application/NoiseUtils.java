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
}
