package vision.core.iterator;

public interface BufferedImageIterator {
	
	public void iterate(final int width, final int height, final int column, final int row, final int r, final int g, final int b);
}
