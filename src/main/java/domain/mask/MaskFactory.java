package domain.mask;



public class MaskFactory {

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
				double gaussian = Math.exp(-(i * i + j * j) / (sigma * sigma))
						/ (2 * Math.PI * sigma * sigma);
				mask.setPixel(i, j, gaussian);
				total += gaussian;
			}
		}
		for (int i = -mask.getWidth() / 2; i <= mask.getWidth() / 2; i++) {
			for (int j = -mask.getHeight() / 2; j <= mask.getHeight() / 2; j++) {
				mask.setPixel(i, j, mask.getValue(i, j)/total);
			}
		}
		
		return mask;
	}

	public static DoubleMask buildPrewittMasks() {
		int size = 3;
		Mask dx = new Mask(size);
		Mask dy = new Mask(size);

		dx.setPixel(-1, -1, -1);
		dx.setPixel(-1, 0, -1);
		dx.setPixel(-1, 1, -1);
		dx.setPixel(1, -1, 1);
		dx.setPixel(1, 0, 1);
		dx.setPixel(1, 1, 1);

		dy.setPixel(-1, -1, -1);
		dy.setPixel(0, -1, -1);
		dy.setPixel(1, -1, -1);
		dy.setPixel(-1, 1, 1);
		dy.setPixel(0, 1, 1);
		dy.setPixel(1, 1, 1);

		return new DoubleMask(dx, dy);
	}

	public static DoubleMask buildSobelMasks() {
		int size = 3;
		Mask dx = new Mask(size);
		Mask dy = new Mask(size);

		dx.setPixel(-1, -1, -1);
		dx.setPixel(-1, 0, -2);
		dx.setPixel(-1, 1, -1);
		dx.setPixel(1, -1, 1);
		dx.setPixel(1, 0, 2);
		dx.setPixel(1, 1, 1);

		dy.setPixel(-1, -1, -1);
		dy.setPixel(0, -1, -2);
		dy.setPixel(1, -1, -1);
		dy.setPixel(-1, 1, 1);
		dy.setPixel(0, 1, 2);
		dy.setPixel(1, 1, 1);

		return new DoubleMask(dx, dy);
	}

	public static DoubleMask buildRobertsMasks() {
		int size = 3;
		Mask dx = new Mask(size);
		Mask dy = new Mask(size);

		dx.setPixel(0, 0, 1);
		dx.setPixel(0, 1, 0);
		dx.setPixel(1, 0, 0);
		dx.setPixel(1, 1, -1);

		dy.setPixel(0, 0, 0);
		dy.setPixel(0, 1, 1);
		dy.setPixel(1, 0, -1);
		dy.setPixel(1, 1, 0);

		return new DoubleMask(dx, dy);
	}

}
