package gui.tp2.filters;

import gui.Panel;
import application.utils.MaskUtils;
import domain.mask.MaskFactory;

@SuppressWarnings("serial")
public class PrewittBorderDetectorDialog extends AbstractBorderDetectorDialog {

	public PrewittBorderDetectorDialog(final Panel panel) {
		super(panel, "Prewitt border detection");
	}
	
	public void applyFunction(SynthesizationType synthesizationType) {
		panel.setImage(MaskUtils.applyDoubleMask(panel.getImage(), MaskFactory.buildPrewittMasks(), synthesizationType));
		panel.repaint();
		dispose();
	}
}
