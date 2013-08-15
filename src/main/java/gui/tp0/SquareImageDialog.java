package gui.tp0;

import gui.Panel;
import application.Creator;
import domain.Image;

@SuppressWarnings("serial")
public class SquareImageDialog extends ImageCreatorDialog {

	public SquareImageDialog(Panel panel) {
		super(panel);
	}
	
	@Override
	protected Image createBinaryImage(int height, int width) {
		return Creator.createSquareImage(height, width);
	}

}
