package everyos.engine.ribbon.renderer.guirenderer.shape;

public class Rectangle implements Cloneable {
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

	public boolean intersects(Rectangle r2) {
		if (r2.x+r2.width<x || x+width<r2.x) {
			return false;
		}
		if (r2.y+r2.height<y || y+height<r2.y) {
			return false;
		}
		return true;
	}
	
	@Override
	public Rectangle clone() {
		return new Rectangle(x, y, width, height);
	}
}
