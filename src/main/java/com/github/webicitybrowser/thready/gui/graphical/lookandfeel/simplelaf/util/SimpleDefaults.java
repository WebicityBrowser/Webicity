package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util;

import com.github.webicitybrowser.thready.drawing.core.text.FontDecoration;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
import com.github.webicitybrowser.thready.drawing.core.text.FontWeight;
import com.github.webicitybrowser.thready.drawing.core.text.source.NamedFontSource;

public final class SimpleDefaults {

	public static FontSettings FONT = new FontSettings(
		new NamedFontSource("Times New Roman"),
		16, FontWeight.NORMAL, new FontDecoration[0]);
	
	private SimpleDefaults() {}
	
}
