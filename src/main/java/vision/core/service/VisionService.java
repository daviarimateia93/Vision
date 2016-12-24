package vision.core.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import japp.model.service.Service;
import japp.util.JAppRuntimeException;
import vision.core.VisionHelper;
import vision.core.image.ImageRepresentation;

public abstract class VisionService extends Service {
	
	public ImageRepresentation process(final InputStream inputStream) {
		try {
			final ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
			final Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(imageInputStream);
			
			if (imageReaders.hasNext()) {
				final ImageReader imageReader = imageReaders.next();
				imageReader.setInput(imageInputStream);
				
				return ImageRepresentation.build(process(imageReader.read(0)), imageReader.getFormatName());
			} else {
				throw new JAppRuntimeException("There is no ImageReader for this image");
			}
		} catch (final IOException exception) {
			throw new JAppRuntimeException(exception);
		}
	}
	
	protected BufferedImage process(final BufferedImage bufferedImage) {
		return process(VisionHelper.convertToImageArray(bufferedImage));
	}
	
	protected abstract BufferedImage process(final int[][][] imageArray);
}
