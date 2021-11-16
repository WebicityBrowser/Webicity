package everyos.engine.ribbon.core.shape;

public class Rectangle {
	
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

	public Rectangle(int[] bounds) {
		this(bounds[0], bounds[1], bounds[2], bounds[3]);
	}

	public boolean intersects(Rectangle r2) {
		return !(
			(r2.x+r2.width<x || x+width<r2.x) ||
			(r2.y+r2.height<y || y+height<r2.y));
	}
	
	public Rectangle intersect(Rectangle r2) {
		int[] intersected = intersectRaw(
			new int[] {x, y, width, height},
			new int[] {r2.getX(), r2.getY(), r2.getWidth(), r2.getHeight()}
		);
		
		return new Rectangle(
			intersected[0], intersected[1],
			intersected[2], intersected[3]
		);
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
	
	public static int[] intersectRaw(int[] rect, int[] rect2) {
		int[] rect1  = new int[] {rect[0], rect[1], rect[2], rect[3]};
		
		if (rect1[0] < rect2[0]) {
			rect1[2] = rect1[2]-(rect2[0]-rect1[0]);
			rect1[0] = rect2[0];
		}
		if (rect1[1] < rect2[1]) {
			rect1[3] = rect1[3]-(rect2[1]-rect1[1]);
			rect1[1] = rect2[1];
		}
		
		int offX = rect1[0]-rect2[0];
		if (offX + rect1[2] > rect2[2]) {
			rect1[2] = rect2[2] - offX;
		}
		
		int offY = rect1[1]-rect2[1];
		if (offY + rect1[3] > rect2[3]) {
			rect1[3] = rect2[3] - offY;
		}
		
		return rect1;
	}
	
	@Override
	public String toString() {
		return "[x="+x+", y="+y+", width="+width+", height="+height+"]";
	}
	
}
