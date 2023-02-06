package everyos.desktop.thready.renderer.skija;

import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.FontMetrics;

public class SkijaFontMetrics implements everyos.desktop.thready.core.graphics.text.FontMetrics {

	private final Font font;
	private final FontMetrics metrics;
	
	private final int[] widthCache = new int[256];

	public SkijaFontMetrics(Font font, FontMetrics metrics) {
		this.font = font;
		this.metrics = metrics;
	}

	@Override
	public float getCharacterWidth(int codePoint) {
		if (codePoint < 256 && widthCache[codePoint] != 0) {
			return widthCache[codePoint];
		}
		
		short glyph = font.getUTF32Glyph(codePoint);
		int width = (int) font.getWidths(new short[] {glyph})[0];
		if (codePoint < 256) {
			widthCache[codePoint] = width;
		}
		
		return width;
	}
	
	@Override
	public float getStringWidth(String text) {
		float totalSize = 0;
		for (int i = 0; i < text.length(); i++) {
			totalSize += getCharacterWidth(text.codePointAt(i));
		}
		
		return totalSize;
	}

	@Override
	public float getHeight() {
		return metrics.getHeight();
	}

	@Override
	public float getLeading() {
		return metrics.getLeading();
	}
	
	@Override
	public float getDescent() {
		return metrics.getDescent();
	}
	
	@Override
	public float getAscent() {
		return metrics.getAscent();
	}
	
	@Override
	public float getCapHeight() {
		return metrics.getCapHeight();
	}
	
	

}
