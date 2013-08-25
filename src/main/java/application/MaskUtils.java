package application;

import domain.Image;
import domain.Image.ColorChannel;
import static domain.Image.ColorChannel.*;

import domain.mask.Mask;

public class MaskUtils {

	public static Image applyMask(Image original, Mask mask) {
		if (original == null || mask == null) {
			return null;
		}
		Image image = new Image(original.getWidth(), original.getHeight(),
				original.getImageFormat(), original.getType());
		applyMask(original, image, mask, RED);
		applyMask(original, image, mask, GREEN);
		applyMask(original, image, mask, BLUE);
		return image;
	}

	private static void applyMask(Image original, Image image, Mask mask,
			ColorChannel color) {
		for (int x = 0; x < original.getWidth(); x++) {
			for (int y = 0; y < original.getHeight(); y++) {
				applyMask(original, image, mask, color, x, y);
			}
		}
	}

	private static void applyMask(Image original, Image image, Mask mask,
			ColorChannel color, int x, int y) {
		double pix = 0;
		int w = image.getWidth(), h = image.getHeight();
		for (int i = -mask.getWidth() / 2; i <= mask.getWidth() / 2; i++) {
			for (int j = -mask.getHeight() / 2; j <= mask.getHeight() / 2; j++) {
				int X = getPoint(x, i, w);
				int Y = getPoint(y, j, h);
				pix += mask.getValue(i, j) * original.getPixel(X, Y, color);
			}
		}
		image.setPixel(x, y, color, pix);
	}

	private static int getPoint(int point, int eps, int max) {
		if (point + eps >= 0 && point + eps < max) {
			return point + eps;
		}
		if (eps > 0)
			return getPoint(point, eps-1, max);
		return getPoint(point, eps+1, max);
		
	}
}
