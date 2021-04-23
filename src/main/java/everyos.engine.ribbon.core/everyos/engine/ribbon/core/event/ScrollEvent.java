package everyos.engine.ribbon.core.event;

public class ScrollEvent implements UIEvent {
	private int x, y;

	public ScrollEvent(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
}
