package gui.tp2.filters;

import gui.Panel;
import domain.mask.Mask;
import domain.mask.MaskFactory;
import domain.mask.MaskFactory.Direction;

@SuppressWarnings("serial")
public class ADifferentFilterDialog extends DirectionalBorderDetectorDialog {

	public ADifferentFilterDialog(final Panel panel) {
		super(panel, "A different filter border detection");
	}

	@Override
	protected Mask getMask(Direction d) {
		return MaskFactory.buildADifferentMask(d);
	}
}
