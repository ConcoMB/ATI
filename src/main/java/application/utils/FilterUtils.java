package application.utils;

import static domain.Image.ChannelType.BLUE;
import static domain.Image.ChannelType.GREEN;
import static domain.Image.ChannelType.RED;
import domain.Image;
import domain.Image.ChannelType;
import domain.SynthetizationType;
import domain.mask.Mask;
import domain.mask.MaskFactory;
import domain.mask.MaskFactory.Direction;

public class FilterUtils {

	public static Image applyCannyFilter(Image original) {
		Image image = (Image) original.clone();
		MaskUtils.applyMask(image, MaskFactory.buildGaussianMask(5, 0.2));
		image = supressNoMaximums(image);
		ThresholdUtils.hysteresisForCanny(image);
		PunctualOperationsUtils.maximize(image);
		return image;
	}
	
	private static Image buildDirectionsImage(Image image, Image Gx, Image Gy) {
		Image direction = image.shallowClone();
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				for (ChannelType c : Image.rgbValues()) {
					double pxGx = Gx.getPixel(x, y, c);
					double pxGy = Gy.getPixel(x, y, c);
					double anAngle = 0;
					if (pxGy != 0) {
						anAngle = Math.atan(pxGx / pxGy);
					}
					anAngle *= (180 / Math.PI);
					direction.setPixel(x, y, c, anAngle);
				}
			}
		}
		return direction;
	}

	public static Image supressNoMaximums(Image image) {
		Image Gx = MaskUtils.applyMask((Image) image.clone(),
				MaskFactory.buildSobelMask(Direction.HORIZONTAL));
		Image Gy = MaskUtils.applyMask((Image) image.clone(),
				MaskFactory.buildSobelMask(Direction.VERTICAL));
		Image direction = buildDirectionsImage(image, Gx, Gy);
		Image[] Gs = { Gx, Gy };
		image = SynthesizationUtils.synthesize(SynthetizationType.ABS, Gs);
		return supressNoMaximums(image, direction);
	}
	
	private static Image supressNoMaximums(Image image, Image direction) {
		Image[] images = new Image[3];
		images[0] = supressNoMaximums(image, direction, RED);
		images[1] = supressNoMaximums(image, direction, GREEN);
		images[2] = supressNoMaximums(image, direction, BLUE);
		return SynthesizationUtils.synthesize(SynthetizationType.MAX, images);
	}

	private static Image supressNoMaximums(Image original,
			Image directionImage, ChannelType c) {
		Image image = original.shallowClone();
		for (int x = 1; x < original.getWidth() - 1; x++) {
			for (int y = 1; y < original.getHeight() - 1; y++) {
				double pixel = original.getPixel(x, y, c);
				if (pixel == 0) {
					continue;
				}

				double direction = directionImage.getPixel(x, y, c);
				double neighbor1 = 0;
				double neighbor2 = 0;
				// Get neighbours
				if (direction >= -90 && direction < -45) {
					neighbor1 = original.getPixel(x, y - 1, c);
					neighbor2 = original.getPixel(x, y + 1, c);
				} else if (direction >= -45 && direction < 0) {
					neighbor1 = original.getPixel(x + 1, y - 1, c);
					neighbor2 = original.getPixel(x - 1, y + 1, c);
				} else if (direction >= 0 && direction < 45) {
					neighbor1 = original.getPixel(x + 1, y, c);
					neighbor2 = original.getPixel(x - 1, y, c);
				} else if (direction >= 45 && direction <= 90) {
					neighbor1 = original.getPixel(x + 1, y + 1, c);
					neighbor2 = original.getPixel(x - 1, y - 1, c);
				}

				// If neighbours are greater than the pixels, erase them from
				// borders.
				if (neighbor1 > pixel || neighbor2 > pixel) {
					image.setPixel(x, y, c, 0);
				} else {
					image.setPixel(x, y, c, pixel);
				}
			}
		}
		return image;
	}

	public static Image applySusanFilter(Image image, double min, double max) {
		Mask mask = MaskFactory.buildSusanMask();
		Image susaned = (Image) image.shallowClone();
		for (int x = 0; x < susaned.getWidth(); x++) {
			for (int y = 0; y < susaned.getHeight(); y++) {
				for (ChannelType c : Image.rgbValues()) {
					double value = c == RED ? 255 : 0;
					double s_ro = MaskUtils.applySusanPixelMask(x, y, mask,
							image, c);
					if (s_ro < max && s_ro > min) {
						susaned.setPixel(x, y, c, value);
					}
				}
			}
		}
		return susaned;
	}
}
