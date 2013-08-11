package gui.tp0;

import gui.Panel;
import application.Creator;
import domain.Image;

public class CircleImageDialog extends ImageCreatorDialog {

	private static final long serialVersionUID = 1L;

	public CircleImageDialog(Panel panel) {
		super(panel);
	}
	
	@Override
	protected Image createBinaryImage(int height, int width) {
		return Creator.createCircleImage(height, width);
	}

}
