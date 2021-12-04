package everyos.engine.ribbon.ui.simple.shape;

import everyos.engine.ribbon.core.shape.Dimension;

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

	public static MutableDimension createFrom(Dimension base) {
		return new MutableDimension(base.getWidth(), base.getHeight());
	}
	
}
