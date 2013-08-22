package gui;


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
import java.util.Deque;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import domain.DrawingContainer;
import domain.Image;

@SuppressWarnings("serial")
public class Panel extends JPanel {

	private Image workingImage = null;
	private Deque<Image> imageHistory = new LinkedList<Image>();
	private Deque<Image> undoStack = new LinkedList<Image>();
	@SuppressWarnings("unused")
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
		workingImage = image;
		imageHistory.push(image);
		((Window) getTopLevelAncestor()).enableTools();
	}

	public Image getImage() {
		return imageHistory.peek();
	}
	
	public Image getTempImage() {
		return workingImage;
	}

	public void setImage(Image image) {
		workingImage = image;
		imageHistory.push((Image) image.clone());
		undoStack.clear();
	}
	
	public void undo() {
		if (!imageHistory.isEmpty()) {
			undoStack.push(imageHistory.pop());
			workingImage = imageHistory.peek();
		}
	}
	
	public void redo() {
		if (!undoStack.isEmpty()) {
			imageHistory.push(undoStack.pop());
			workingImage = imageHistory.peek();
		}
	}

	public void initKeyBindings() {
		String UNDO = "Undo action key";
		String REDO = "Redo action key";
		Action undoAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				workingImage = getImage();
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

	public void setTempImage(Image image) {
		workingImage = image;
	}
}
