package everyos.engine.ribbon.core.shape;

public class Margin {
	
	private final double percent;
	private final int offset;
	
	public Margin(double xp, int offset) {
		this.percent = xp;
		this.offset = offset;
	}
	
	public int calculate(int parent) {
		return (int) (parent*percent+offset);
	}

	public double getRelative() {
		return percent;
	}
	
	public int getAbsolute() {
		return offset;
	}
	
}
