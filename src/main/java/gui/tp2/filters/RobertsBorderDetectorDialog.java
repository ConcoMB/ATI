package gui.tp2.filters;

import gui.Panel;
import application.utils.MaskUtils;
import domain.mask.MaskFactory;

@SuppressWarnings("serial")
public class RobertsBorderDetectorDialog extends AbstractBorderDetectorDialog {


	public RobertsBorderDetectorDialog(final Panel panel) {
		super(panel, "Roberts border detection");
	}
	
	public void applyFunction(SynthesizationType synthesizationType) {
		panel.setImage(MaskUtils.applyDoubleMask(panel.getImage(), MaskFactory.buildRobertsMasks(), synthesizationType));
		panel.repaint();
		dispose();
	}
	
}
