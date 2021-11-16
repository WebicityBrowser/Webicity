package everyos.engine.ribbon.core.graphics.paintfill;

public interface Color extends PaintFill {
	
	public static final Color BLACK = Color.of(0, 0, 0);
	public static final Color WHITE = Color.of(255, 255, 255);
	public static final Color DARK_GRAY = Color.of(102, 102, 102);
	public static final Color LIGHT_GRAY = Color.of(153, 153, 153);
	public static final Color RED = Color.of(255, 0, 0);
	public static final Color GREEN = Color.of(0, 255, 0);
	public static final Color BLUE = Color.of(0, 0, 255);
	
	public static final Color TRANSPARENT = Color.of(0, 0, 0, 0);
	
	public static final Color DARK_GREY = DARK_GRAY;
	public static final Color LIGHT_GREY = LIGHT_GRAY;
	
	int getAlpha();
	int getRed();
	int getGreen();
	int getBlue();
	
	public static Color of(int a, int r, int g, int b) {
		return new ARGBColor(a, r, g, b);
	}
	
	public static Color of(int r, int g, int b) {
		return new ARGBColor(r, g, b);
	}
	
	public static Color of(int rgb) {
		return new ARGBColor(rgb);
	}
	
	public static Color of(int rgb, int alpha) {
		return new ARGBColor(rgb, alpha);
	}
	
}
