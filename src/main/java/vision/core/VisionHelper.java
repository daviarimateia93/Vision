package vision.core;

import java.awt.Color;
import java.awt.image.BufferedImage;

import vision.core.iterator.BufferedImageIterator;
import vision.core.iterator.GrayImageIterator;
import vision.core.iterator.RGBImageIterator;

public class VisionHelper {
	
	public static int[][][] empty(final int[][][] imageArray) {
		return new int[getWidth(imageArray)][getHeight(imageArray)][3];
	}
	
	public static int[][] empty(final int[][] imageArray) {
		return new int[getWidth(imageArray)][getHeight(imageArray)];
	}
	
	public static int[][][] rgbEmpty(final int[][] imageArray) {
		return new int[getWidth(imageArray)][getHeight(imageArray)][3];
	}
	
	public static int[][] grayEmpty(final int[][][] imageArray) {
		return new int[getWidth(imageArray)][getHeight(imageArray)];
	}
	
	public static int[][][] copy(final int[][][] imageArray) {
		final int[][][] newImageArray = empty(imageArray);
		
		for (int i = 0; i < imageArray.length; i++) {
			for (int j = 0; j < imageArray[i].length; j++) {
				newImageArray[i][j][0] = imageArray[i][j][0];
				newImageArray[i][j][1] = imageArray[i][j][1];
				newImageArray[i][j][2] = imageArray[i][j][2];
			}
		}
		
		return newImageArray;
	}
	
	public static int[][] copy(final int[][] imageArray) {
		final int[][] newImageArray = empty(imageArray);
		
		for (int i = 0; i < imageArray.length; i++) {
			for (int j = 0; j < imageArray[i].length; j++) {
				newImageArray[i][j] = imageArray[i][j];
			}
		}
		
		return newImageArray;
	}
	
	public static void iterate(final BufferedImage bufferedImage, final BufferedImageIterator bufferedImageIterator) {
		final int width = bufferedImage.getWidth();
		final int height = bufferedImage.getHeight();
		
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				final Color color = new Color(bufferedImage.getRGB(column, row));
				
				bufferedImageIterator.iterate(width, height, column, row, color.getRed(), color.getGreen(), color.getBlue());
			}
		}
	}
	
	public static void iterate(final int[][][] imageArray, final RGBImageIterator rgbImageIterator) {
		final int width = getWidth(imageArray);
		final int height = getHeight(imageArray);
		
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				final int r = imageArray[column][row][0];
				final int g = imageArray[column][row][1];
				final int b = imageArray[column][row][2];
				
				rgbImageIterator.iterate(imageArray, width, height, column, row, r, g, b);
			}
		}
	}
	
	public static void iterate(final int[][] imageArray, final GrayImageIterator grayImageIterator) {
		final int width = getWidth(imageArray);
		final int height = getHeight(imageArray);
		
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				grayImageIterator.iterate(imageArray, width, height, column, row, imageArray[column][row]);
			}
		}
	}
	
	public static int truncate(final double value) {
		return (int) (value < 0 ? 0 : value > 255 ? 255 : value);
	}
	
	public static int getWidth(final int[][][] imageArray) {
		return imageArray.length;
	}
	
	public static int getHeight(final int[][][] imageArray) {
		return imageArray[0].length;
	}
	
	public static int getWidth(final int[][] imageArray) {
		return imageArray.length;
	}
	
	public static int getHeight(final int[][] imageArray) {
		return imageArray[0].length;
	}
	
	public static int[][][] convertToImageArray(final BufferedImage bufferedImage) {
		final int bufferedImageWidth = bufferedImage.getWidth();
		final int bufferedImageHeight = bufferedImage.getHeight();
		final int[][][] imageArray = new int[bufferedImageWidth][bufferedImageHeight][3];
		
		iterate(bufferedImage, (width, height, column, row, r, g, b) -> {
			imageArray[column][row][0] = r;
			imageArray[column][row][1] = g;
			imageArray[column][row][2] = b;
		});
		
		return imageArray;
	}
	
	public static BufferedImage convertToBufferedImage(final int[][][] imageArray) {
		final int imageArrayWidth = getWidth(imageArray);
		final int imageArrayHeight = getHeight(imageArray);
		final BufferedImage bufferedImage = new BufferedImage(imageArrayWidth, imageArrayHeight, BufferedImage.TYPE_INT_RGB);
		
		iterate(imageArray, (data, width, height, column, row, r, g, b) -> {
			final Color color = new Color(r, g, b);
			
			bufferedImage.setRGB(column, row, color.getRGB());
		});
		
		return bufferedImage;
	}
	
	public static BufferedImage convertToBufferedImage(final int[][] imageArray) {
		final int imageArrayWidth = getWidth(imageArray);
		final int imageArrayHeight = getHeight(imageArray);
		final BufferedImage bufferedImage = new BufferedImage(imageArrayWidth, imageArrayHeight, BufferedImage.TYPE_INT_RGB);
		
		iterate(imageArray, (data, width, height, column, row, value) -> {
			final Color color = new Color(value, value, value);
			
			bufferedImage.setRGB(column, row, color.getRGB());
		});
		
		return bufferedImage;
	}
}
