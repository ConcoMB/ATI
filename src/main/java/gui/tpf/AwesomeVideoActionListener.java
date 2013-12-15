package gui.tpf;

import gui.MessageFrame;
import gui.Panel;
import gui.Window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenu;

import org.apache.sanselan.ImageReadException;

import application.Loader;
import domain.Image;

public abstract class AwesomeVideoActionListener implements ActionListener {

	private JMenu menu;

	public AwesomeVideoActionListener(JMenu menu) {
		this.menu = menu;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Panel panel = ((Window) menu.getTopLevelAncestor()).getPanel();
		Image image = panel.getImage();
		if (image == null) {
			return;
		}
		String[] splitted = Loader.getCurrentFile().getAbsolutePath()
				.split("/");
		String fileName = splitted[splitted.length - 1];
		int x = fileName.indexOf('.');

		StringBuffer num = new StringBuffer();
		boolean end = false;
		while (!end && x > 0) {
			char n = fileName.charAt(--x);
			if (n >= '0' && n <= '9')
				num.append(n);
			else
				end = true;
		}
		num.reverse();
		String[] nameSplitted = Loader.getCurrentFile().getAbsolutePath()
				.split(num.toString() + ".");
		if (nameSplitted.length < 2) {
			new MessageFrame("Invalid Image");
			return;
		}
		String extension = nameSplitted[1];
		String filePrefix = nameSplitted[0];
		int i = Integer.valueOf(num.toString());
		i++;
		boolean read = true;
		panel.setImage(doMagic(image));
		panel.paintImmediately(0, 0, panel.getWidth(), panel.getHeight());
		while (read) {
			File currentFile = new File(filePrefix + getNum(num.length(), i) + "." + extension);
			if (!currentFile.exists()) {
				read = false;
				break;
			}
			try {
				image = Loader.loadImage(currentFile);
			} catch (ImageReadException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			panel.setImage(doMagic(image));
			panel.paintImmediately(0, 0, panel.getWidth(), panel.getHeight());
			i++;
		}
	}

	private String getNum(int length, int i) {
		StringBuffer s = new StringBuffer();
		s.append(i);
		s.reverse();
		while(s.length() < length) {
			s.append(0);
		}
		s.reverse();
		return s.toString();
	}
	
	protected abstract Image doMagic(Image image);
}
