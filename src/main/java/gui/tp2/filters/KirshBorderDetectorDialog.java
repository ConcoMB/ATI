package gui.tp2.filters;

import gui.Panel;
import application.utils.MaskUtils;
import domain.SynthetizationType;
import domain.mask.MaskFactory;

@SuppressWarnings("serial")
public class KirshBorderDetectorDialog extends BorderDetectorDialog {
	
	public KirshBorderDetectorDialog(final Panel panel) {
		super(panel, "Kirsh border detection");
	}

	public void applyFunction(SynthetizationType synthesizationType) {
		panel.setImage(MaskUtils.applyQuadrupleMask(panel.getImage(), MaskFactory.buildKirshMasks(), synthesizationType));
		panel.repaint();
		dispose();
	}
}
