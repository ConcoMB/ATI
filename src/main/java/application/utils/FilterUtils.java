package application.utils;

import domain.Image;
import domain.Image.ColorChannel;
import domain.mask.Mask;
import domain.mask.MaskFactory;


public class FilterUtils {

	public static Image applySusanFilter(Image image, double min, double max) {
		Mask mask = MaskFactory.buildSusanMask();
		Image susaned = (Image) image.clone();
		for (int x = 0; x < susaned.getWidth(); x++) {
			for (int y = 0; y < susaned.getHeight(); y++) {
					for(ColorChannel c : ColorChannel.values()){
					double s_ro = MaskUtils.applySusanPixelMask(x, y, mask, susaned, c);
					if (s_ro < max && s_ro > min) {
						susaned.setPixel(x, y, c, 0);
					} else {
						susaned.setPixel(x, y, c, 255);
					}
				}
			}
		}
		return susaned;
	}

	
}
