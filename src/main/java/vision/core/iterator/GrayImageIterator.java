package vision.core.iterator;

public interface GrayImageIterator {
	
	public void iterate(final int[][] data, final int width, final int height, final int column, final int row, final int value);
}
