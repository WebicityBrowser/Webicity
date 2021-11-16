package everyos.engine.ribbon.renderer.skijarenderer;

import org.jetbrains.skija.Font;
import org.jetbrains.skija.FontMetrics;
import org.jetbrains.skija.FontMgr;
import org.jetbrains.skija.FontSlant;
import org.jetbrains.skija.FontStyle;
import org.jetbrains.skija.FontWidth;
import org.jetbrains.skija.Typeface;

import everyos.engine.ribbon.core.graphics.font.FontInfo;
import everyos.engine.ribbon.core.graphics.font.RibbonFontMetrics;

public class RibbonSkijaFont implements RibbonFontMetrics {
	
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
		return (int) metrics.getDescent();
	}
	
	@Override
	public String toString() {
		return font.getTypeface().getFamilyName();
	}
	
	protected Font getRaw() {
		return font;
	}
	
	public static RibbonSkijaFont of(FontInfo info) {
		FontStyle style = new FontStyle(info.getWeight(), FontWidth.NORMAL, FontSlant.UPRIGHT);
		Typeface typeface = manager.matchFamilyStyle(info.getName(), style);
		Font font = new Font(typeface, info.getSize());
		
		return new RibbonSkijaFont(font);
	}
	
}
