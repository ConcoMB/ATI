package application;

import static domain.Image.ColorChannel.BLUE;
import static domain.Image.ColorChannel.GREEN;
import static domain.Image.ColorChannel.RED;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import domain.Image;
import domain.Image.ColorChannel;
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
				int a = getPoint(x, i, w);
				int b = getPoint(y, j, h);
				pix += mask.getValue(i, j) * original.getPixel(a, b, color);
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
	
	public static Image applyMedianMask(Image original, int maskWidth, int maskHeight) {
		if (original == null) {
			return null;
		}
		Image image = new Image(original.getWidth(), original.getHeight(),
				original.getImageFormat(), original.getType());
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				image.setPixel(x, y, RED, getMedianPixel(original, RED, x, y, maskWidth, maskHeight));
				image.setPixel(x, y, GREEN, getMedianPixel(original, GREEN, x, y, maskWidth, maskHeight));
				image.setPixel(x, y, BLUE, getMedianPixel(original, BLUE, x, y, maskWidth, maskHeight));
			}
		}
		return image;
	}
	
	private static double getMedianPixel(Image image, ColorChannel color, int x, int y, int maskWidth, int maskHeight) {
		List<Double> pixelsAffected = new ArrayList<Double>();
		for (int i = -maskWidth / 2; i <= maskWidth / 2; i++) {
			for (int j = -maskHeight / 2; j <= maskHeight / 2; j++) {
				if (image.validPixel(x + i, y + j)) {
					pixelsAffected.add(image.getPixel(x + i, y + j, color));
				}
			}
		}
		Collections.sort(pixelsAffected, new Comparator<Double>() {
			public int compare(Double arg0, Double arg1) {
				return (int)(arg0 - arg1);
			}
		});
		double val;
		int size = pixelsAffected.size();
		if (size % 2 == 0) {
			val = ((double)(pixelsAffected.get(size/2) + pixelsAffected.get(size/2 + 1))) / 2;
		} else {
			val = pixelsAffected.get(size/2);
		}
		return val;
	}

}
