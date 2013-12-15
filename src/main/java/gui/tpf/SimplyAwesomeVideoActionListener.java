package gui.tpf;

import javax.swing.JMenu;

import application.utils.ThresholdUtils;
import domain.Image;

public class SimplyAwesomeVideoActionListener extends
		AwesomeVideoActionListener {

	public SimplyAwesomeVideoActionListener(JMenu menu) {
		super(menu);
	}

	@Override
	protected Image doMagic(Image image) {
		return ThresholdUtils.awesomeThresholding(image);
	}

}
