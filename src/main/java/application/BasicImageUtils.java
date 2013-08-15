package application;

import static domain.Image.ImageFormat.BMP;
import static domain.Image.ImageType.COLOR;
import static domain.Image.ImageType.GREYSCALE;

import java.awt.Color;

import domain.Image;

public class BasicImageUtils {
	
	public static Image createSquareImage(int height, int width) {
		Image binaryImage = new Image(height, width, BMP, GREYSCALE);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// Analyzes if the point is in the black or white square
				boolean fitsInSquareByWidth = (x > width / 4 && x < 3 * (width / 4));
				boolean fitsInSquareByHeight = (y > height / 4 && y < 3 * (height / 4));
				Color colorToApply = (fitsInSquareByWidth && fitsInSquareByHeight) ? Color.WHITE : Color.BLACK;
				binaryImage.setPixel(x, y, colorToApply.getRGB());
			}
		}
		return binaryImage;
	}

	public static Image createWhiteImage(int height, int width) {
		Image whiteImage = new Image(height, width, BMP, GREYSCALE);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				whiteImage.setPixel(x, y, Color.WHITE.getRGB());
			}
		}
		return whiteImage;
	}

	public static Image createDegrade(boolean isColor, int height, int width,
			int color1, int color2) {
		Image degradee = null;
		if (isColor) {
			degradee = new Image(height, width, BMP, COLOR);
		} else {
			degradee = new Image(height, width, BMP, GREYSCALE);
		}
		Color c1 = new Color(color1);
		Color c2 = new Color(color2);
		Color currentColor = new Color(color1);
		float redFactor = (float) (c2.getRed() - c1.getRed()) / height;
		float greenFactor = (float) (c2.getGreen() - c1.getGreen()) / height;
		float blueFactor = (float) (c2.getBlue() - c1.getBlue()) / height;
		float red = 0;
		float green = 0;
		float blue = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				degradee.setPixel(x, y, currentColor.getRGB());
			}
			red = red + redFactor;
			green = green + greenFactor;
			blue = blue + blueFactor;
			currentColor = new Color(c1.getRGB() + (int) ((int) red * 0x010000)
					+ (int) ((int) green * 0x000100)
					+ (int) ((int) blue * 0x000001));
		}
		return degradee;
	}

	public static Image createCircleImage(int height, int width) {
		Image binaryImage = new Image(height, width, BMP, GREYSCALE);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				double aTerm = Math.pow(x - width / 2, 2);
				double bTerm = Math.pow(y - height / 2, 2);
				double rTerm = Math.pow(height/4, 2);
				Color colorToApply = (aTerm + bTerm) <= rTerm ? Color.WHITE : Color.BLACK;
				binaryImage.setPixel(x, y, colorToApply.getRGB());
			}
		}
		return binaryImage;
	}
	
	public static Image crop(int height, int width, int x, int y, Image original) {
		Image image = new Image(height, width, original.getImageFormat(), original.getType());
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < height; j++) {
				image.setPixel(i, j, original.getPixel(i + x, j + y));
			}
		}
		return image;
	}
}
