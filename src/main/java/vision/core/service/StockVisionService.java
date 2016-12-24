package vision.core.service;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import vision.core.VisionHelper;
import vision.core.VisionProcessor;

public class StockVisionService extends VisionService {
	
	@Override
	protected BufferedImage process(final int[][][] imageArray) {
		final int[][] processedImageArray = new VisionProcessor(imageArray).rgbAverageGrayScale().grayBinary(50).grayDilation().grayErosion().grayErosion().getCurrentValue();
		final int[][] regions = VisionProcessor.segmentation(processedImageArray);
		final Map<Integer, List<Integer[]>> regionsMap = new HashMap<>();
		final int[][][] newImageArray = VisionHelper.rgbEmpty(regions);
		
		VisionHelper.iterate(regions, (data, width, height, column, row, value) -> {
			if (value != 0) {
				if (!regionsMap.containsKey(value)) {
					regionsMap.put(Integer.valueOf(value), new ArrayList<>());
				}
				
				final List<Integer[]> entry = regionsMap.get(value);
				entry.add(new Integer[] { column, row });
			}
		});
		
		for (final Map.Entry<Integer, List<Integer[]>> entry : regionsMap.entrySet()) {
			final int r = ThreadLocalRandom.current().nextInt(0, 255 + 1);
			final int g = ThreadLocalRandom.current().nextInt(0, 255 + 1);
			final int b = ThreadLocalRandom.current().nextInt(0, 255 + 1);
			
			for (final Integer[] columnAndRow : entry.getValue()) {
				newImageArray[columnAndRow[0]][columnAndRow[1]][0] = r;
				newImageArray[columnAndRow[0]][columnAndRow[1]][1] = g;
				newImageArray[columnAndRow[0]][columnAndRow[1]][2] = b;
			}
		}
		
		return VisionHelper.convertToBufferedImage(newImageArray);
	}
}
