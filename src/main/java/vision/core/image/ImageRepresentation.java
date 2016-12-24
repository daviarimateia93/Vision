package vision.core.image;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageRepresentation extends BufferedImage {
	
	private final String formatName;
	
	public ImageRepresentation(final ColorModel colorModel, final WritableRaster writableRaster, final boolean isAlphaPremultiplied, final String formatName) {
		super(colorModel, writableRaster, isAlphaPremultiplied, null);
		
		this.formatName = formatName;
	}
	
	public String getFormatName() {
		return formatName;
	}
	
	public String getContentType() {
		return "image/" + formatName;
	}
	
	public byte[] convertToBytes() throws IOException {
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		ImageIO.write(this, formatName, byteArrayOutputStream);
		
		return byteArrayOutputStream.toByteArray();
	}
	
	public static ImageRepresentation build(final BufferedImage bufferedImage, final String formatName) {
		final ColorModel colorModel = bufferedImage.getColorModel();
		final WritableRaster writableRaster = bufferedImage.copyData(bufferedImage.getRaster().createCompatibleWritableRaster());
		final boolean isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
		
		return new ImageRepresentation(colorModel, writableRaster, isAlphaPremultiplied, formatName);
	}
}
