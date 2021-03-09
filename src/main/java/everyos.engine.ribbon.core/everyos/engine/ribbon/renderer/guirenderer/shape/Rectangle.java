package everyos.engine.ribbon.renderer.guirenderer.shape;

public class Rectangle {
	public int x;
	public int y;
	public int width;
	public int height;

	public Rectangle(int x, int y, int l, int h) {
		this.x = x;
		this.y = y;
		this.width = l;
		this.height = h;
	}
	
	public Object clone() {
		return new Rectangle(x, y, width, height);
	}
}
