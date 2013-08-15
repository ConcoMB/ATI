package gui.tp0;

import gui.ExtensionFilter;
import gui.MessageFrame;
import gui.Panel;
import gui.Window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.filechooser.FileFilter;

import org.apache.sanselan.ImageWriteException;

import application.Loader;
import application.Saver;
import domain.Image;

@SuppressWarnings("serial")
public class FileMenu extends JMenu {

	public JMenuItem saveImage = new JMenuItem("Save image");
	public JMenuItem cropImage = new JMenuItem("Crop image");
	
	public FileMenu() {
		super("File");
		this.setEnabled(true);
		JMenuItem newWindow = new JMenuItem("New window");
		newWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window window = new Window();
				window.setVisible(true);
			}
		});
		JMenuItem loadImage = new JMenuItem("Load image");
		loadImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileFilter type = new ExtensionFilter("Images", new String[] {
						".pgm", ".PGM", ".ppm", ".PPM", ".bmp", ".BMP" });
				chooser.addChoosableFileFilter(type);
				chooser.setAcceptAllFileFilterUsed(false);
				chooser.setFileFilter(type);
				chooser.showOpenDialog(FileMenu.this);
				File arch = chooser.getSelectedFile();
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (arch != null) {
					Image image = null;
					try {
						image = Loader.loadImage(arch);
					} catch (Exception ex) {
                        ex.printStackTrace();
						new MessageFrame("Couldn't load the image");
					}
					if (image != null) {
						panel.loadImage(image);
						panel.repaint();
					}
				}
			}
		});
		JMenuItem loadRaw = new JMenuItem("Load raw image");
		loadRaw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileFilter type = new ExtensionFilter("Raw images",
						new String[] { ".raw", ".RAW" });
				chooser.addChoosableFileFilter(type);
				chooser.setAcceptAllFileFilterUsed(false);
				chooser.setFileFilter(type);
				chooser.showOpenDialog(FileMenu.this);
				File arch = chooser.getSelectedFile();
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (arch != null) {
					JDialog rawParams = new RawImageDialog(panel, arch);
					rawParams.setVisible(true);
				}
			}
		});

		saveImage.setEnabled(false);
		saveImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser selector = new JFileChooser();
				selector.setApproveButtonText("Save");
				selector.showSaveDialog(FileMenu.this);
				File arch = selector.getSelectedFile();
				if (arch != null) {
					Image image = (((Window) getTopLevelAncestor()).getPanel()
							.getWorkingImage());
					try {
						Saver.saveImage(arch, image);
					} catch (ImageWriteException ex) {
						new MessageFrame("Couldn't save the image");
					} catch (IOException ex) {
						new MessageFrame("Couldn't save the image");
					}
				}
			}
		});

		cropImage.setEnabled(false);
		cropImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				JDialog crop = new CropDialog(panel);
				crop.setVisible(true);
//				panel.loadImage(image);
				panel.repaint();
			}
		});
		
		JMenuItem binaryImage = new JMenuItem("Square image");
		binaryImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				JDialog binaryImage = new SquareImageDialog(panel);
				binaryImage.setVisible(true);
			}
		});
		JMenuItem circleBinaryImage = new JMenuItem("Circle image");
		circleBinaryImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				JDialog binaryImage = new CircleImageDialog(panel);
				binaryImage.setVisible(true);
			}
		});
		JMenuItem degradeBW = new JMenuItem("Grey degradee");
		degradeBW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				JDialog degrade = new DegradeDialog(panel, false);
				degrade.setVisible(true);
			}
		});
		JMenuItem degradeColor = new JMenuItem("Color degradee");
		degradeColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				JDialog degrade = new DegradeDialog(panel, true);
				degrade.setVisible(true);
			}
		});
		this.add(newWindow);
		this.add(new JSeparator());
		this.add(loadImage);
		this.add(loadRaw);
		this.add(saveImage);
		this.add(cropImage);
		this.add(new JSeparator());
		this.add(binaryImage);
		this.add(circleBinaryImage);
		this.add(degradeBW);
		this.add(degradeColor);
	}
}