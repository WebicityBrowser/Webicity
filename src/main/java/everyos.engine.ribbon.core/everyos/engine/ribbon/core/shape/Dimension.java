package everyos.engine.ribbon.core.shape;

public class Dimension {
	private int width;
	private int height;

	public Dimension(int l, int h) {
		this.width = l;
		this.height = h;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public MutableDimension toMutable() {
		return new MutableDimension(width, height);
	}
}
