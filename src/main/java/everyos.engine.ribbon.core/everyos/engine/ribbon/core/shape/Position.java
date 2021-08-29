package everyos.engine.ribbon.core.shape;

public class Position implements Cloneable {
	private final int x;
	private final int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public Position clone() {
		return new Position(x, y);
	}
}
