package gui.tp2.filters;

import gui.Panel;
import domain.mask.Mask;
import domain.mask.MaskFactory;
import domain.mask.MaskFactory.Direction;

@SuppressWarnings("serial")
public class AFilterDialog extends DirectionalBorderDetectorDialog {

	public AFilterDialog(final Panel panel) {
		super(panel, "A filter border detection");
	}

	@Override
	protected Mask getMask(Direction d) {
		return MaskFactory.buildAMask(d);
	}
}
