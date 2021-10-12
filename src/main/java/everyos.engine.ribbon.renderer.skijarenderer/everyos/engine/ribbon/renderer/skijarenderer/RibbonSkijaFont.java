package everyos.engine.ribbon.renderer.skijarenderer;

import org.jetbrains.skija.Font;
import org.jetbrains.skija.FontMetrics;
import org.jetbrains.skija.FontMgr;
import org.jetbrains.skija.FontStyle;
import org.jetbrains.skija.Typeface;

import everyos.engine.ribbon.core.rendering.RibbonFont;

public class RibbonSkijaFont implements RibbonFont {
	
	private static final FontMgr manager = FontMgr.getDefault();
	
	private final Font font;
	private final FontMetrics metrics;
	private final int[] widthCache;

	public RibbonSkijaFont(Font font) {
		this.font = font;
		this.metrics = font.getMetrics();
		this.widthCache = new int[256];
	}

	@Override
	public int getHeight() {
		return (int) metrics.getHeight();
	}

	@Override
	public int getCharWidth(int ch) {
		if (ch < 256 && widthCache[ch] != 0) {
			return widthCache[ch];
		}
		
		short glyph = font.getUTF32Glyph(ch);
		int width = (int) font.getWidths(new short[] {glyph})[0];
		if (ch < 256) {
			widthCache[ch] = width;
		}
		
		return width;
	}

	@Override
	public int getPaddingHeight() {
		return (int) metrics.getDescent() + 1;
	}
	
	public Font getRaw() {
		return font;
	}

	public static RibbonSkijaFont of(String name, int weight, int size) {
		Typeface typeface = manager.matchFamilyStyle(name, FontStyle.NORMAL);
		Font font = new Font(typeface, size);
		
		return new RibbonSkijaFont(font);
	}
}
