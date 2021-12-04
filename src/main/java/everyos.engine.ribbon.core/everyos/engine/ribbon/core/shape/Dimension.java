package everyos.engine.ribbon.core.shape;

public class Dimension {
	
	private final int width;
	private final int height;

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
	
	// TODO: Handle indefinite width/height here instead of everywhere
}
