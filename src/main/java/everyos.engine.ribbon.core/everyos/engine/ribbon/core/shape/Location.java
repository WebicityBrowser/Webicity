package everyos.engine.ribbon.core.shape;

public class Location {
	
	private final Margin x;
	private final Margin y;
	
	public Location(double xp, int xo, double yp, int yo) {
		x = new Margin(xp, xo);
		y = new Margin(yp, yo);
	}
	
	public Margin getX() {
		return x;
	}
	
	public Margin getY() {
		return y;
	}
	
}
