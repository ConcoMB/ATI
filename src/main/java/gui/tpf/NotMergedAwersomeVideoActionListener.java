package gui.tpf;

import javax.swing.JMenu;

import application.utils.ThresholdUtils;
import domain.Image;

public class NotMergedAwersomeVideoActionListener extends
		AwesomeVideoActionListener {

	public NotMergedAwersomeVideoActionListener(JMenu menu) {
		super(menu);
	}

	@Override
	protected Image doMagic(Image image) {
		return ThresholdUtils.notMergedAwesomeThresholding(image);
	}


}
