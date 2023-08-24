package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util;

import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;

public final class WebStringUtils {
	
	private WebStringUtils() {}

	// This is a modified version of the method that
	// Minecraftian14 used in the Ribbon branch of Webicity.
	// It only works on single-line strings.
	public static String trim(FontMetrics fontMetrics, String text, float fixedWidth) {
		float curWidth = fontMetrics.getStringWidth(text);

		// If it's already a small string, return it as it is.
		if (curWidth <= fixedWidth) return text;

		// Else keep trimming it by one character to fit it under fixedWidth
		while (curWidth > fixedWidth && text.length() > 0) {
			text = text.substring(0, text.length() - 1);
			curWidth = fontMetrics.getStringWidth(ellipseText(text));
		}

		return ellipseText(text);
	}

	private static String ellipseText(String text) {
		// If the text is still sufficiently long, replace last three char with ...
		if (text.length() > 8) {
			return text.substring(0, text.length() - 3) + "...";
		}
		return text;
	}

}
