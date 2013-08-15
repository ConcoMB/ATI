package gui.tp0;

import gui.Panel;
import application.Creator;
import domain.Image;

@SuppressWarnings("serial")
public class CircleImageDialog extends ImageCreatorDialog {

	public CircleImageDialog(Panel panel) {
		super(panel);
	}
	
	@Override
	protected Image createBinaryImage(int height, int width) {
		return Creator.createCircleImage(height, width);
	}

}
