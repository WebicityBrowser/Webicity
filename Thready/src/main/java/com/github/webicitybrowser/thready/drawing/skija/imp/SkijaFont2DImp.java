package com.github.webicitybrowser.thready.drawing.skija.imp;

import java.util.ArrayList;
import java.util.List;

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
	
	private final FontSettings fontSettings;
	private final Font[] fallbackFonts;
	private final FontMetrics metrics;
	
	private final short[] glyphCache = new short[256];
	private final Font[] effectiveFontCache = new Font[256];

	public SkijaFont2DImp(FontSettings fontSettings, Font[] fallbackFonts, FontMetrics metrics) {
		this.fontSettings = fontSettings;
		this.fallbackFonts = fallbackFonts;
		this.metrics = metrics;
	}

	@Override
	public FontMetrics getMetrics() {
		return this.metrics;
	}

	@Override
	public FontSettings getSettings() {
		return this.fontSettings;
	}

	public Font getEffectiveFont(int codePoint) {
		if (codePoint < 256 && effectiveFontCache[codePoint] != null) {
			return effectiveFontCache[codePoint];
		}

		Font font = fallbackFonts[0];
		for (Font fallbackFont : fallbackFonts) {
			short glyph = fallbackFont.getUTF32Glyph(codePoint);
			if (glyph != 0) {
				font = fallbackFont;
				break;
			}
		}

		if (codePoint < 256) {
			effectiveFontCache[codePoint] = font;
		}

		return font;
	}
	
	public short getCharacterGlyph(int codePoint) {
		if (codePoint < 256 && glyphCache[codePoint] != 0) {
			return glyphCache[codePoint];
		}
		
		short glyph = getEffectiveFont(codePoint).getUTF32Glyph(codePoint);
		if (codePoint < 256) {
			glyphCache[codePoint] = glyph;
		}

		return glyph;
	}

	public static Font2D createFor(FontSettings settings) {
		Font[] fonts = loadFonts(settings);
		FontMetrics metrics = new SkijaFontMetricsImp(fonts, settings);
		
		return new SkijaFont2DImp(settings, fonts, metrics);
	}

	private static Font[] loadFonts(FontSettings settings) {
		List<Font> fonts = new ArrayList<>();
		FontSource[] sources = settings.fontSources();
		FontStyle style = new FontStyle(settings.fontWeight(), FontWidth.NORMAL, FontSlant.UPRIGHT);
		for (int i = 0; i < sources.length; i++) {
			Font font = loadNamedFont(
				((NamedFontSource) sources[i]),
				style, settings.fontSize());
			if (font != null) fonts.add(font);
		}
		fonts.add(new Font(manager.matchFamilyStyle("Times New Roman", style), settings.fontSize()));

		return fonts.toArray(Font[]::new);
	}

	private static Font loadNamedFont(NamedFontSource fontSource, FontStyle style, float fontSize) {
		String fontName = fontSource.getName();
		Typeface typeface = manager.matchFamilyStyle(fontName, style);
		if (typeface == null) return null;
		return new Font(typeface, fontSize);
	}

	@Override
	public Font[] getRawFonts() {
		return fallbackFonts;
	}

}
