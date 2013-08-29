package gui.tp2.filters;

import gui.Panel;
import application.utils.MaskUtils;
import domain.mask.MaskFactory;

@SuppressWarnings("serial")
public class SobelBorderDetectorDialog extends AbstractBorderDetectorDialog {

	public SobelBorderDetectorDialog(final Panel panel) {
		super(panel, "Sobel border detection");
	}

	public void applyFunction(SynthesizationType synthesizationType) {
		panel.setImage(MaskUtils.applyDoubleMask(panel.getImage(), MaskFactory.buildSobelMasks(), synthesizationType));
		panel.repaint();
		dispose();
	}
}
