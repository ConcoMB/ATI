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
	
}
