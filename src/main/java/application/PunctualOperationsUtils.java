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
}
