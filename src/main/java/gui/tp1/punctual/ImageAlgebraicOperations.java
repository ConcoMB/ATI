package gui.tp1.punctual;

import gui.ExtensionFilter;
import gui.MessageFrame;
import gui.Panel;
import gui.Window;
import gui.menus.PunctualOperationsMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileFilter;

import application.Loader;
import domain.Image;

@SuppressWarnings("serial")
public abstract class ImageAlgebraicOperations extends JMenuItem {
	
	PunctualOperationsMenu menu;

	public ImageAlgebraicOperations(String s, final PunctualOperationsMenu menu) {
		super(s);
		this.menu = menu;

		this.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Panel panel = (((Window) menu.getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JFileChooser chooser = new JFileChooser();
				Image panelImage = panel.getImage();
				FileFilter type = new ExtensionFilter("Images", new String[] {
						".pgm", ".PGM", ".ppm", ".PPM", ".bmp", ".BMP" });
				chooser.addChoosableFileFilter(type);
				chooser.setAcceptAllFileFilterUsed(false);
				chooser.setFileFilter(type);
				chooser.showOpenDialog(menu);
				File file = chooser.getSelectedFile();
				if (file != null) {
					Image image = null;
					try {
						image = Loader.loadImage(file);
					} catch (Exception ex) {
						new MessageFrame("Couldn't load the image");
						return;
					}

					if (image.getHeight() != panelImage.getHeight()
							|| image.getWidth() != panelImage.getWidth()) {

						new MessageFrame("Images must be of the same size");
						return;
					}
					try {
						panel.setImage(doOperation(panelImage, image));
						panel.repaint();
					} catch (IllegalArgumentException i) {
						new MessageFrame(i.getMessage());
					}
				}

			}
		});
	}

	protected abstract Image doOperation(Image img1, Image img2);

}
