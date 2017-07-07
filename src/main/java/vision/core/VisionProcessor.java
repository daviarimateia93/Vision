package vision.core;

import java.util.ArrayList;
import java.util.List;

import japp.util.Reference;

public class VisionProcessor {
	
	protected Object currentValue;
	
	@SuppressWarnings("unchecked")
	public <T> T getCurrentValue() {
		return (T) currentValue;
	}
	
	public VisionProcessor(final Object currentValue) {
		this.currentValue = currentValue;
	}
	
	protected VisionProcessor assignAndReturn(final Object currentValue) {
		this.currentValue = currentValue;
		
		return this;
	}
	
	public VisionProcessor rgbAverageGrayScale() {
		final int[][] newImageArray = VisionHelper.grayEmpty((int[][][]) currentValue);
		
		VisionHelper.iterate((int[][][]) currentValue, (data, width, height, column, row, r, g, b) -> {
			newImageArray[column][row] = (r + g + b) / 3;
		});
		
		return assignAndReturn(newImageArray);
	}
	
	public VisionProcessor rgbIntensityGrayScale() {
		final int[][] newImageArray = VisionHelper.grayEmpty((int[][][]) currentValue);
		
		VisionHelper.iterate((int[][][]) currentValue, (data, width, height, column, row, r, g, b) -> {
			newImageArray[column][row] = (int) (0.299 * r + 0.587 * g + 0.114 * b);
		});
		
		return assignAndReturn(newImageArray);
	}
	
	public VisionProcessor rgbBright(int bright) {
		final int[][][] newImageArray = VisionHelper.empty((int[][][]) currentValue);
		
		VisionHelper.iterate((int[][][]) currentValue, (data, width, height, column, row, r, g, b) -> {
			newImageArray[column][row][0] = VisionHelper.truncate(r + bright);
			newImageArray[column][row][1] = VisionHelper.truncate(g + bright);
			newImageArray[column][row][2] = VisionHelper.truncate(b + bright);
		});
		
		return assignAndReturn(newImageArray);
	}
	
	public VisionProcessor grayBright(int bright) {
		final int[][] newImageArray = VisionHelper.empty((int[][]) currentValue);
		
		VisionHelper.iterate((int[][]) currentValue, (data, width, height, column, row, value) -> {
			newImageArray[column][row] = VisionHelper.truncate(value + bright);
		});
		
		return assignAndReturn(newImageArray);
	}
	
	public VisionProcessor rgbContrast(float contrast) {
		final int[][][] newImageArray = VisionHelper.empty((int[][][]) currentValue);
		
		VisionHelper.iterate((int[][][]) currentValue, (data, width, height, column, row, r, g, b) -> {
			newImageArray[column][row][0] = VisionHelper.truncate(r * contrast);
			newImageArray[column][row][1] = VisionHelper.truncate(g * contrast);
			newImageArray[column][row][2] = VisionHelper.truncate(b * contrast);
		});
		
		return assignAndReturn(newImageArray);
	}
	
	public VisionProcessor grayContrast(float contrast) {
		final int[][] newImageArray = VisionHelper.empty((int[][]) currentValue);
		
		VisionHelper.iterate((int[][]) currentValue, (data, width, height, column, row, value) -> {
			newImageArray[column][row] = VisionHelper.truncate(value * contrast);
		});
		
		return assignAndReturn(newImageArray);
	}
	
	public VisionProcessor grayBinary(int threshold) {
		final int[][] newImageArray = VisionHelper.empty((int[][]) currentValue);
		
		VisionHelper.iterate((int[][]) currentValue, (data, width, height, column, row, value) -> {
			newImageArray[column][row] = value >= threshold ? 255 : 0;
		});
		
		return assignAndReturn(newImageArray);
	}
	
	public VisionProcessor rgbNegative() {
		final int[][][] newImageArray = VisionHelper.empty((int[][][]) currentValue);
		
		VisionHelper.iterate((int[][][]) currentValue, (data, width, height, column, row, r, g, b) -> {
			newImageArray[column][row][0] = 255 - r;
			newImageArray[column][row][1] = 255 - g;
			newImageArray[column][row][2] = 255 - b;
		});
		
		return assignAndReturn(newImageArray);
	}
	
	public VisionProcessor grayNegative() {
		final int[][] newImageArray = VisionHelper.empty((int[][]) currentValue);
		
		VisionHelper.iterate((int[][]) currentValue, (data, width, height, column, row, value) -> {
			newImageArray[column][row] = 255 - value;
		});
		
		return assignAndReturn(newImageArray);
	}
	
	public VisionProcessor grayDilation() {
		final int[][] newImageArray = VisionHelper.empty((int[][]) currentValue);
		
		VisionHelper.iterate((int[][]) currentValue, (data, width, height, column, row, value) -> {
			if (data[column][row] == 255 && row > 0 && row < height - 1 && column > 0 && column < width - 1) {
				newImageArray[column - 1][row - 1] = 255;
				newImageArray[column][row - 1] = 255;
				newImageArray[column + 1][row - 1] = 255;
				newImageArray[column - 1][row] = 255;
				newImageArray[column][row] = 255;
				newImageArray[column + 1][row] = 255;
				newImageArray[column - 1][row + 1] = 255;
				newImageArray[column][row + 1] = 255;
				newImageArray[column + 1][row + 1] = 255;
			}
		});
		
		return assignAndReturn(newImageArray);
	}
	
	public VisionProcessor grayErosion() {
		return grayNegative().grayDilation().grayNegative();
	}
	
	public static int[][][] edge(final int[][][] imageArray, final int edge) {
		final int[][][] newImageArray = new int[VisionHelper.getWidth(imageArray) + (edge * 2)][VisionHelper.getHeight(imageArray) + (edge * 2)][3];
		
		VisionHelper.iterate(imageArray, (data, width, height, column, row, r, g, b) -> {
			if (column + edge >= 0 && row + edge >= 0 && (edge >= 0 || (edge < 0 && column <= width + (edge * 2) && row <= height + (edge * 2)))) {
				newImageArray[column + edge][row + edge][0] = r;
				newImageArray[column + edge][row + edge][1] = g;
				newImageArray[column + edge][row + edge][2] = b;
			}
		});
		
		return newImageArray;
	}
	
	public static int[][] edge(final int[][] imageArray, final int edge) {
		final int[][] newImageArray = new int[VisionHelper.getWidth(imageArray) + (edge * 2)][VisionHelper.getHeight(imageArray) + (edge * 2)];
		
		VisionHelper.iterate(imageArray, (data, width, height, column, row, value) -> {
			if (column + edge >= 0 && row + edge >= 0 && (edge >= 0 || (edge < 0 && column <= width + (edge * 2) && row <= height + (edge * 2)))) {
				newImageArray[column + edge][row + edge] = value;
			}
		});
		
		return newImageArray;
	}
	
	public static int[][] segmentation(final int[][] imageArray) {
		final int[][] edgedImageArray = edge(imageArray, 1);
		final int[][] regions = VisionHelper.empty(edgedImageArray);
		final List<Integer> junctions = new ArrayList<Integer>();
		final Reference<Integer> regionCounter = new Reference<Integer>(0);
		
		junctions.add(regionCounter.get());
		
		VisionHelper.iterate(edgedImageArray, (data, width, height, column, row, value) -> {
			if (value == 255 && row > 0 && row < height - 1 && column > 0 && column < width - 1) {
				// Rule #1 -> left == 0 && top == 0
				if ((regions[column - 1][row] == 0) && (regions[column][row - 1] == 0)) {
					regionCounter.set(regionCounter.get() + 1);
					regions[column][row] = regionCounter.get();
					junctions.add(regionCounter.get());
				}
				
				// Rule #2 -> left != 0 && top == 0
				if ((regions[column - 1][row] != 0) && (regions[column][row - 1] == 0)) {
					regions[column][row] = regions[column - 1][row];
				}
				
				// Rule #3 -> left == 0 && top != 0
				if ((regions[column - 1][row] == 0) && (regions[column][row - 1] != 0)) {
					regions[column][row] = regions[column][row - 1];
				}
				
				// Rule #4 -> left != 0 && top != 0 && left == top
				if ((regions[column - 1][row] != 0) && (regions[column][row - 1] != 0) && (regions[column - 1][row] == regions[column][row - 1])) {
					regions[column][row] = regions[column - 1][row];
				}
				
				// Rule #5 -> left != 0 && top != 0 && left != top
				if ((regions[column - 1][row] != 0) && (regions[column][row - 1] != 0) && (regions[column - 1][row] != regions[column][row - 1])) {
					regions[column][row] = regions[column - 1][row];
					junctions.set(regions[column][row - 1], regions[column - 1][row]);
				}
			}
		});
		
		// Arrange junctions
		for (int i = 1; i < junctions.size(); i++) {
			int currentValue = junctions.get(i);
			int lastValue = -1;
			
			while (currentValue != junctions.get(currentValue) && lastValue != junctions.get(currentValue)) {
				lastValue = currentValue;
				currentValue = junctions.get(currentValue);
			}
			
			junctions.set(i, currentValue);
		}
		
		// Arrange regions
		VisionHelper.iterate(regions, (data, width, height, column, row, value) -> {
			data[column][row] = junctions.get(value);
		});
		
		return edge(regions, -1);
	}
}
