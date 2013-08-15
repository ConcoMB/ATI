package gui.tp1;

import application.PunctualOperationsUtils;
import domain.Image;

@SuppressWarnings("serial")
public class SubstractImagesItem extends ImageAlgebraicOperations {

	public SubstractImagesItem(PunctualOperationsMenu t) {
		super("Substract images", t);
	}

	@Override
	protected Image doOperation(Image panelImage, Image image) {
		return PunctualOperationsUtils.substract(panelImage, image);
	}

}
