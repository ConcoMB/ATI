package application;


import ij.ImagePlus;
import ij.io.FileSaver;
import domain.Image;
import org.apache.sanselan.ImageFormat;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.Sanselan;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Saver {
	
	public static void saveImage(File arch, Image image) throws ImageWriteException, IOException {
		
		String[] cadena = (arch.getName()).split("\\.");
		String extension = cadena[cadena.length-1];

        ImageFormat format;

        BufferedImage bi = ColorUtilities.populateEmptyBufferedImage(image);
        if(!extension.equals("raw")){
			format = ColorUtilities.toSanselanImageFormat(image.getImageFormat());
            Sanselan.writeImage(bi, arch, format, null);
        } else {
            new FileSaver(new ImagePlus("", bi)).saveAsRaw(arch.getAbsoluteFile().toString());
//			throw new UnsupportedOperationException("Still not supporting saving raw");
		}


	}

}
