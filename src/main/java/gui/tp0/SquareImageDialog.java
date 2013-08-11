package gui.tp0;

import gui.Panel;
import application.Creator;
import domain.Image;

public class SquareImageDialog extends ImageCreatorDialog {

	private static final long serialVersionUID = 1L;

	public SquareImageDialog(Panel panel) {
		super(panel);
	}
	
	@Override
	protected Image createBinaryImage(int height, int width) {
		return Creator.createSquareImage(height, width);
	}

}
