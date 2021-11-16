package everyos.engine.ribbon.core.graphics.paintfill;

public class ARGBColor implements Color {
	private final int color;

	public ARGBColor(int a, int r, int g, int b) {
		color = (a << 24) + (r << 16) + (g << 8) + b;
	}
	
	public ARGBColor(int r, int g, int b) {
		this(255, r, g, b);
	}
	
	// Thanks to joeyjoejoe from Discord for suggesting these constructors!
	public ARGBColor(int rgb) {
		color = (255 << 24) + rgb;
	}
	
	public ARGBColor(int rgb, int alpha) {
		color = (alpha << 24) + rgb;
	}
	//

	public int getAlpha() {
		return (color >>> 24) & 0xFF;
	}
	
	public int getRed() {
		return (color >>> 16) & 255;
	}
	
	public int getGreen() {
		return (color >>> 8) & 255;
	}
	
	public int getBlue() {
		return color & 255;
	}
	
	@Override
	public String toString() {
		return "Color [a=" + getAlpha() + ", r="+getRed() + ", g=" + getGreen() + ", b=" + getBlue() + "]";
	}
}
