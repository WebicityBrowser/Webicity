package everyos.engine.ribbon.renderer.guirenderer.shape;

public class Margin {
	public double percent;
	public int offset;
	public Margin(double xp, int offset) {
		this.percent = xp;
		this.offset = offset;
	}
	public int calculate(int parent) {
		return (int) (parent*percent+offset);
	}
}
