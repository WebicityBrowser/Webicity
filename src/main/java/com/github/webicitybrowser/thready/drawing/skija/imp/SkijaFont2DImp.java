package com.github.webicitybrowser.thready.drawing.skija.imp;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
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

	public static Font2D createFor(FontSettings settings) {
		Font font = loadNamedFont(settings);
		FontMetrics metrics = new SkijaFontMetricsImp(font, settings, font.getMetrics());
		
		return new SkijaFont2DImp(font, metrics);
	}

	private static Font loadNamedFont(FontSettings settings) {
		FontStyle style = new FontStyle(settings.fontWeight(), FontWidth.NORMAL, FontSlant.UPRIGHT);
		for (int i = 0; i < settings.fontSources().length; i++) {
			String fontName = ((NamedFontSource) settings.fontSources()[i]).getName();
			Typeface typeface = manager.matchFamilyStyle(fontName, style);
			if (typeface == null) continue;
			return new Font(typeface, settings.fontSize());
		}
		
		return new Font(null, settings.fontSize());
	}

}
