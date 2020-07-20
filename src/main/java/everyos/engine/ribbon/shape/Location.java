package everyos.engine.ribbon.shape;

public class Location {
	public Margin x;
	public Margin y;
	public Location(double xp, int xo, double yp, int yo) {
		x = new Margin(xp, xo);
		y = new Margin(yp, yo);
	}
}
