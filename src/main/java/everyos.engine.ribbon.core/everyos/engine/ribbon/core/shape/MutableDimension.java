package everyos.engine.ribbon.core.shape;

public class MutableDimension {
	private int width;
	private int height;

	public MutableDimension(int l, int h) {
		this.width = l;
		this.height = h;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public void setWidth(int l) {
		this.width = l;
	}
	
	public void setHeight(int h) {
		this.height = h;
	}
	
	public Dimension freeze() {
		return new Dimension(width, height);
	}
}
