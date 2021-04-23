package everyos.engine.ribbon.core.shape;

public class Rectangle implements Cloneable {
	private final int x;
	private final int y;
	private final int width;
	private final int height;

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

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	@Override
	public String toString() {
		return
			"[x="+x+", y="+y+", width="+width+", height="+height+"]";
	}
}
