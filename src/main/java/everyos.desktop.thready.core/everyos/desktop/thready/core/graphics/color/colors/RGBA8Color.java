package everyos.desktop.thready.core.graphics.color.colors;

import everyos.desktop.thready.core.graphics.color.RawColor;
import everyos.desktop.thready.core.graphics.color.formats.RGBA8ColorFormat;
import everyos.desktop.thready.core.graphics.color.imp.InternalColorImp;

public class RGBA8Color implements RGBA8ColorFormat {

	private final int color;

	public RGBA8Color(int r, int g, int b, int a) {
		color = (r << 24) + (g << 16) + (b << 8) + a;
	}
	
	public RGBA8Color(int r, int g, int b) {
		this(r, g, b, 255);
	}
	
	// Thanks to joeyjoejoe from Discord for suggesting these constructors!
	public RGBA8Color(int rgb) {
		this(rgb, 255);
	}
	
	public RGBA8Color(int rgb, int alpha) {
		color = (rgb << 8) + alpha;
	}
	//
	
	@Override
	public int getRed() {
		return (color >>> 24) & 0xFF;
	}
	
	@Override
	public int getGreen() {
		return (color >>> 16) & 255;
	}
	
	@Override
	public int getBlue() {
		return (color >>> 8) & 255;
	}
	
	@Override
	public int getAlpha() {
		return color & 255;
	}
	
	@Override
	public RawColor toRawColor() {
		return InternalColorImp.ofRGBA8(getRed(), getGreen(), getBlue(), getAlpha());
	}
	
	@Override
	public String toString() {
		return "Color [a=" + getAlpha() + ", r="+getRed() + ", g=" + getGreen() + ", b=" + getBlue() + "]";
	}
	
	public static RGBA8Color fromRawColor(RawColor rawColor) {
		InternalColorImp color = (InternalColorImp) rawColor;
		return new RGBA8Color(color.getRed8(), color.getGreen8(), color.getBlue8(), color.getAlpha8());
	}
	
}
