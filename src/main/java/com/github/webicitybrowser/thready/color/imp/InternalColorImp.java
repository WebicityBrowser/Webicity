package com.github.webicitybrowser.thready.color.imp;

import com.github.webicitybrowser.thready.color.RawColor;

//Colors are internally stored as RGBA16
public class InternalColorImp implements RawColor {
	
	private final long color;
	
	private InternalColorImp(long color) {
		this.color = color;
	}

	
	public int getRed16() {
		return (int) ((color >>> 48) & 0xFFFF);
	}

	
	public int getRed8() {
		return getRed16() >>> 8;
	}

	
	public int getGreen16() {
		return (int) ((color >>> 32) & 0xFFFF);
	}

	
	public int getGreen8() {
		return getGreen16() >>> 8;
	}

	
	public int getBlue16() {
		return (int) ((color >>> 16) & 0xFFFF);
	}

	
	public int getBlue8() {
		return getBlue16() >>> 8;
	}

	
	public int getAlpha16() {
		return (int) (color & 0xFFFF);
	}

	
	public int getAlpha8() {
		return getAlpha16() >>> 8;
	}
	
	public static RawColor ofRGBA8(int r, int g, int b, int a) {
		long color =
			(((long) r) << 56) +
			(((long) g) << 40) +
			(((long) b) << 24) +
			(((long) a) << 8);
		
		return new InternalColorImp(color);
	}

	public static RawColor ofRGB8(int r, int g, int b) {
		return ofRGBA8(r, g, b, 255);
	}
	
}