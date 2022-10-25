package everyos.desktop.thready.core.graphics.color.colors;

import everyos.desktop.thready.core.graphics.color.formats.RGBA8ColorFormat;

public class RGBA8Color implements RGBA8ColorFormat {

	private final int color;

	public RGBA8Color(int r, int g, int b, int a) {
		color = (a << 24) + (r << 16) + (g << 8) + b;
	}
	
	public RGBA8Color(int r, int g, int b) {
		this(255, r, g, b);
	}
	
	// Thanks to joeyjoejoe from Discord for suggesting these constructors!
	public RGBA8Color(int rgb) {
		color = (255 << 24) + rgb;
	}
	
	public RGBA8Color(int rgb, int alpha) {
		color = (alpha << 24) + rgb;
	}
	//

	@Override
	public int getAlpha() {
		return (color >>> 24) & 0xFF;
	}
	
	@Override
	public int getRed() {
		return (color >>> 16) & 255;
	}
	
	@Override
	public int getGreen() {
		return (color >>> 8) & 255;
	}
	
	@Override
	public int getBlue() {
		return color & 255;
	}
	
	@Override
	public String toString() {
		return "Color [a=" + getAlpha() + ", r="+getRed() + ", g=" + getGreen() + ", b=" + getBlue() + "]";
	}
	
}
