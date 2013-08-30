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
				mask.setPixel(i, j, mask.getValue(i, j) / total);
			}
		}

		return mask;
	}

	public static DoubleMask buildPrewittMasks() {
		double[][] dxValues = { { -1, -1, -1 }, { 0, 0, 0 }, { 1, 1, 1 } };
		double[][] dyValues = { { -1, 0, 1 }, { -1, 0, 1 }, { -1, 0, 1 } };
		Mask dx = new Mask(dxValues);
		Mask dy = new Mask(dyValues);
		return new DoubleMask(dx, dy);
	}

	public static DoubleMask buildSobelMasks() {
		double[][] dxValues = { { -1, -2, -1 }, { 0, 0, 0 }, { 1, 2, 1 } };
		double[][] dyValues = { { -1, 0, 1 }, { -2, 0, 2 }, { -1, 0, 1 } };
		Mask dx = new Mask(dxValues);
		Mask dy = new Mask(dyValues);
		return new DoubleMask(dx, dy);
	}

	public static DoubleMask buildRobertsMasks() {
		double[][] dxValues = { { 0, 0, 0 }, { 0, 1, 0 }, { 0, 0, -1 } };
		double[][] dyValues = { { 0, 0, 0 }, { 0, 0, 1 }, { 0, -1, 0 } };
		Mask dx = new Mask(dxValues);
		Mask dy = new Mask(dyValues);
		return new DoubleMask(dx, dy);
	}

	public static QuadrupleMask buildKirshMasks() {
		double[][] aValues = { { 5, 5, 5 }, { -3, 0, -3 }, { -3, -3, -3 } };
		double[][] bValues = { { -3, 5, 5 }, { -3, 0, 5 }, { -3, -3, -3 } };
		double[][] cValues = { { -3, -3, -3 }, { 5, 0, -3 }, { 5, 5, -3 } };
		double[][] dValues = { { 5, -3, -3 }, { 5, 0, -3 }, { 5, -3, -3 } };
		
		Mask maskA = new Mask(aValues);
		Mask maskB = new Mask(bValues);
		Mask maskC = new Mask(cValues);
		Mask maskD = new Mask(dValues);

		return new QuadrupleMask(maskA, maskB, maskC, maskD);
	}

}
