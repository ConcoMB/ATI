package application;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.sanselan.ImageFormat;
import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;

import domain.Image;

public class Loader {

	// TODO: remove this wire
	public static File currentFile;

	public static Image loadImage(File arch) throws ImageReadException,
			IOException {

		currentFile = arch;

		BufferedImage bi = Sanselan.getBufferedImage(arch);
		ImageInfo info = Sanselan.getImageInfo(arch);
		Image.ImageFormat format;

		if (info.getFormat() == ImageFormat.IMAGE_FORMAT_BMP) {
			format = Image.ImageFormat.BMP;
		} else if (info.getFormat() == ImageFormat.IMAGE_FORMAT_PGM) {
			format = Image.ImageFormat.PGM;
		} else if (info.getFormat() == ImageFormat.IMAGE_FORMAT_PPM) {
			format = Image.ImageFormat.PPM;
		} else {
			throw new IllegalStateException();
		}

		if (bi.getType() == BufferedImage.TYPE_INT_RGB) {
			return new ColorImage(bi, format, Image.ImageType.RGB, false);
		} else if (bi.getType() == BufferedImage.TYPE_BYTE_GRAY) {
			return new ColorImage(bi, format, Image.ImageType.GRAYSCALE, false);
		} else {
			throw new IllegalStateException("Image wasn't RGB nor Grayscale");
		}

	}

	public static Image loadRaw(File file, int width, int height)
			throws IOException {

		BufferedImage ret;
		byte[] data = getBytesFromFile(file);
		ret = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = ret.getRaster();
		int k = 0;
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				raster.setSample(i, j, 0, data[k]);
				k = k + 1;
			}
		}
		Image image = new ColorImage(height, width, Image.ImageFormat.RAW,
				Image.ImageType.GRAYSCALE);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				image.setRGBPixel(i, j, ret.getRGB(i, j));
			}
		}
		return image;

	}

	@SuppressWarnings("resource")
	/**
	 * Read the bytes from the file and returns it
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		byte[] bytes = new byte[(int) file.length()];
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}
		if (offset < bytes.length) {
			throw new IOException();
		}
		is.close();
		return bytes;
	}

}
