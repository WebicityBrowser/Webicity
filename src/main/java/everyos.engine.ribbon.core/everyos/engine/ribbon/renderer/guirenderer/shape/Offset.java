package everyos.engine.ribbon.renderer.guirenderer.shape;

public class Offset {
	private double ox;
	private double oy;
	public Offset(double d, double oy) {
		this.ox = d; this.oy = oy;
	}
	public int applyX(int width) {
		return (int) (ox*width);
	}
	public int applyY(int height) {
		return (int) (oy*height);
	}
}
