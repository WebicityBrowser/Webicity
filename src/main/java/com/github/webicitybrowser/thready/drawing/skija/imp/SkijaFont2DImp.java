package com.github.webicitybrowser.thready.drawing.skija.imp;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
import com.github.webicitybrowser.thready.drawing.core.text.source.FontSource;
import com.github.webicitybrowser.thready.drawing.core.text.source.NamedFontSource;
import com.github.webicitybrowser.thready.drawing.skija.SkijaFont2D;

import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.FontMgr;
import io.github.humbleui.skija.FontSlant;
import io.github.humbleui.skija.FontStyle;
import io.github.humbleui.skija.FontWidth;
import io.github.humbleui.skija.Typeface;

public class SkijaFont2DImp implements SkijaFont2D {

	private static final FontMgr manager = FontMgr.getDefault();
	
	private final Font font;
	private final FontMetrics metrics;
	
	private final short[] glyphCache = new short[256];

	public SkijaFont2DImp(Font font, FontMetrics metrics) {
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

	public static Font2D createFor(FontSource source, FontSettings settings) {
		Font font = loadNamedFont(source, settings);
		FontMetrics metrics = new SkijaFontMetricsImp(font, font.getMetrics());
		
		return new SkijaFont2DImp(font, metrics);
	}

	private static Font loadNamedFont(FontSource source, FontSettings info) {
		FontStyle style = new FontStyle(info.fontWeight(), FontWidth.NORMAL, FontSlant.UPRIGHT);
		String fontName = ((NamedFontSource) source).getName();
		Typeface typeface = manager.matchFamilyStyle(fontName, style);
		Font font = new Font(typeface, info.fontSize());
		
		return font;
	}

}
