package everyos.engine.ribbon.ui.simple.helper;

import everyos.engine.ribbon.core.shape.Rectangle;

public class RectangleBuilder {
	private int x;
	private int y;
	private int width;
	private int height;

	public RectangleBuilder(int x, int y, int l, int h) {
		this.x = x;
		this.y = y;
		this.width = l;
		this.height = h;
	}
	
	public RectangleBuilder(Rectangle base) {
		this(base.getX(), base.getY(), base.getWidth(), base.getHeight());
	}

	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public Rectangle build() {
		return new Rectangle(x, y, width, height);
	}
}
