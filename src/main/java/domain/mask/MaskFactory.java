package domain.mask;

public class MaskFactory {

	public static Mask buildMeanMask(int width, int height) {
		Mask mask = new Mask(width, height);
		double totalPixels = width * height;
		for (int i = -width / 2; i <= width / 2; i++) {
			for (int j = -height / 2; j <= height / 2; j++) {
				mask.setPixel(i, j, 1 / totalPixels);
			}
		}
		return mask;
	}

	public static Mask buildEdgeEnhancementMask(int width, int height) {
		Mask mask = new Mask(width, height);
		double pixelAmount = width * height;
		for (int i = -mask.getWidth() / 2; i <= mask.getWidth() / 2; i++) {
			for (int j = -mask.getHeight() / 2; j <= mask.getHeight() / 2; j++) {
				mask.setPixel(i, j, -1);
			}
		}
		mask.setPixel(0, 0, (pixelAmount - 1));
		return mask;
	}

	public static Mask buildGaussianMask(int size, double sigma) {
        if (size % 2 == 0) {
            size++;
        }
		Mask mask = new Mask(size);
		double total = 0;
		for (int i = -mask.getWidth() / 2; i <= mask.getWidth() / 2; i++) {
			for (int j = -mask.getHeight() / 2; j <= mask.getHeight() / 2; j++) {
				double gaussianFunction = (1 / (2 * Math.PI * Math
						.pow(sigma, 2)))
						* Math.exp(-((Math.pow(i, 2) + Math.pow(j, 2))
								/ (Math.pow(sigma, 2))));
				total += gaussianFunction;
				mask.setPixel(i, j, gaussianFunction);
			}
		}
		for (int i = -mask.getWidth() / 2; i <= mask.getWidth() / 2; i++) {
			for (int j = -mask.getHeight() / 2; j <= mask.getHeight() / 2; j++) {
				double oldPixel = mask.getValue(i, j);
				mask.setPixel(i, j, oldPixel / total);
			}
		}
		return mask;
	}

}
