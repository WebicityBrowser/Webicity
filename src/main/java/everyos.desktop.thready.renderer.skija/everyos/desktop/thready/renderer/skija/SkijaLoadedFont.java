package everyos.desktop.thready.renderer.skija;

import everyos.desktop.thready.core.graphics.text.FontInfo;
import everyos.desktop.thready.core.graphics.text.FontMetrics;
import everyos.desktop.thready.core.graphics.text.LoadedFont;
import everyos.desktop.thready.core.graphics.text.NamedFont;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.FontMgr;
import io.github.humbleui.skija.FontSlant;
import io.github.humbleui.skija.FontStyle;
import io.github.humbleui.skija.FontWidth;
import io.github.humbleui.skija.Typeface;

public class SkijaLoadedFont implements LoadedFont {
	
	private static final FontMgr manager = FontMgr.getDefault();
	
	private final Font font;
	private final FontMetrics metrics;
	
	private final short[] glyphCache = new short[256];

	public SkijaLoadedFont(Font font, FontMetrics metrics) {
		this.font = font;
		this.metrics = metrics;
	}

	@Override
	public FontMetrics getMetrics() {
		return this.metrics;
	}
	
	public short getCharacterGlyph(int codePoint) {
		if (codePoint < 256 && glyphCache[codePoint] != 0) {
			return glyphCache[codePoint];
		}
		
		short glyph = font.getUTF32Glyph(codePoint);
		if (codePoint < 256) {
			glyphCache[codePoint] = glyph;
		}
		
		return glyph;
	}
	
	public Font getRaw() {
		return this.font;
	}

	public static SkijaLoadedFont createFor(FontInfo info) {
		Font font = loadNamedFont(info);
		FontMetrics metrics = new SkijaFontMetrics(font, font.getMetrics());
		
		return new SkijaLoadedFont(font, metrics);
	}

	private static Font loadNamedFont(FontInfo info) {
		FontStyle style = new FontStyle(info.fontWeight(), FontWidth.NORMAL, FontSlant.UPRIGHT);
		String fontName = ((NamedFont) info.font()).getName();
		Typeface typeface = manager.matchFamilyStyle(fontName, style);
		Font font = new Font(typeface, info.fontSize());
		
		return font;
	}

}
