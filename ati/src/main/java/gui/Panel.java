package gui;


import static domain.Image.ColorChannel.BLUE;
import static domain.Image.ColorChannel.GREEN;
import static domain.Image.ColorChannel.RED;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import domain.DrawingContainer;
import domain.Image;

public class Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Image workingImage = null;
	private Image image = null;
	private Image lastImg = null;
	private Window window;
	private VolatileImage vImg;
	private DrawingContainer drawingContainer;

	public Panel(Window window) {
		this.window = window;
	}

	void renderOffscreen() {
		do {
			if (vImg.validate(getGraphicsConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE) {
				// old vImg doesn't work with new GraphicsConfig; re-create it
				vImg = this.getGraphicsConfiguration()
						.createCompatibleVolatileImage(workingImage.getWidth(),
								workingImage.getHeight());
			}
			Graphics2D g = vImg.createGraphics();
			g.drawImage(workingImage.getBufferedImage(),
					workingImage.getWidth(), workingImage.getHeight(), null);
			g.dispose();
		} while (vImg.contentsLost());
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (workingImage != null) {
			BufferedImage bufferedImage = toCompatibleImage(workingImage
					.getBufferedImage());
			g.drawImage(bufferedImage, 0, 0, null);
		}

		if (drawingContainer != null) {
			for (Point p : drawingContainer.in) {
				g.setColor(Color.RED);
				g.drawRect(p.x, p.y, 1, 1);
			}
			for (Point p : drawingContainer.inner) {
				g.setColor(Color.BLUE);
				g.drawRect(p.x, p.y, 1, 1);
			}
		}
	}

	private BufferedImage toCompatibleImage(BufferedImage image) {
		// obtain the current system graphical settings
		GraphicsConfiguration gfx_config = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();

		/*
		 * if image is already compatible and optimized for current system
		 * settings, simply return it
		 */
		if (image.getColorModel().equals(gfx_config.getColorModel()))
			return image;

		// image is not optimized, so create a new image that is
		BufferedImage new_image = gfx_config.createCompatibleImage(
				image.getWidth(), image.getHeight(), image.getTransparency());

		// get the graphics context of the new image to draw the old image on
		Graphics2D g2d = (Graphics2D) new_image.getGraphics();

		// actually draw the image and dispose of context no longer needed
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();

		// return the new optimized image
		return new_image;
	}

	public void loadImage(Image image) {
		this.workingImage = image;
		this.image = image;
		((Window) getTopLevelAncestor()).enableTools();
	}

	public boolean setPixel(String xText, String yText, String colorText) {

		int x = 0;
		int y = 0;
		int color = 0;
		try {
			x = Integer.valueOf(xText);
			y = Integer.valueOf(yText);
			color = Integer.valueOf(colorText);
		} catch (NumberFormatException ex) {
			new MessageFrame("Los valores ingresados son incorrectos");
			return false;
		}
		setAllPixels(x, y, color);

		this.repaint();
		return true;
	}

	private void setAllPixels(int x, int y, double color) {
		this.workingImage.setPixel(x, y, RED, color);
		this.workingImage.setPixel(x, y, GREEN, color);
		this.workingImage.setPixel(x, y, BLUE, color);
	}

	public Image getWorkingImage() {
		return workingImage;
	}

	public void setWorkingImage(Image workingImage) {
		this.workingImage = workingImage;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.workingImage = image;
		this.image = (Image) image.clone();
	}

	public void initKeyBindings() {
		String UNDO = "Undo action key";
		String REDO = "Redo action key";
		Action undoAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				setWorkingImage(getImage());
				drawingContainer = new DrawingContainer();
				repaint();
			}
		};
		Action redoAction = new
		AbstractAction() {
			public void actionPerformed(ActionEvent e) {

			}
		};
		getActionMap().put(UNDO, undoAction);
		getActionMap().put(REDO, redoAction);
		InputMap[] inputMaps = new InputMap[] {
				getInputMap(JComponent.WHEN_FOCUSED),
				getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT),
				getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW), };
		for (InputMap i : inputMaps) {
			i.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit
					.getDefaultToolkit().getMenuShortcutKeyMask()), UNDO);
			i.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit
					.getDefaultToolkit().getMenuShortcutKeyMask()), REDO);
		}
	}

	public void loadTracker(DrawingContainer t) {
		this.drawingContainer = t;
	}

	public DrawingContainer getDrawingContainer() {
		if (drawingContainer == null) {
			drawingContainer = new DrawingContainer();
		}
		return drawingContainer;
	}
}
