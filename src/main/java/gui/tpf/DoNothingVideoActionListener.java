package gui.tpf;

import javax.swing.JMenu;

import domain.Image;

public class DoNothingVideoActionListener extends AwesomeVideoActionListener {

	public DoNothingVideoActionListener(JMenu menu) {
		super(menu);
	}

	@Override
	protected Image doMagic(Image image) {
		return image;

	}
}
