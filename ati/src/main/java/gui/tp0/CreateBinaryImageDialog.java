package gui.tp0;

import gui.Panel;
import application.Creator;
import domain.Image;

public class CreateBinaryImageDialog extends CreateImageDialog {

	private static final long serialVersionUID = 1L;

	public CreateBinaryImageDialog(Panel panel) {
		super(panel);
	}

	@Override
	protected Image createBinaryImage(int height, int width) {
		return Creator.createBinaryImage(height, width);
	}

}
