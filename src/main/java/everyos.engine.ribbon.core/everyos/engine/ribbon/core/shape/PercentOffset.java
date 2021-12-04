package everyos.engine.ribbon.core.shape;

public class PercentOffset {
	
	private final double offsetX;
	private final double offsetY;
	
	public PercentOffset(double d, double oy) {
		this.offsetX = d;
		this.offsetY = oy;
	}
	
	public int applyX(int width) {
		return (int) (offsetX * width);
	}
	
	public int applyY(int height) {
		return (int) (offsetY * height);
	}
	
}
