package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util;

import com.github.webicitybrowser.thready.drawing.core.text.FontDecoration;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
import com.github.webicitybrowser.thready.drawing.core.text.CommonFontWeights;
import com.github.webicitybrowser.thready.drawing.core.text.source.FontSource;
import com.github.webicitybrowser.thready.drawing.core.text.source.NamedFontSource;

public final class SimpleDefaults {

	public static FontSettings FONT = new FontSettings(
		new FontSource[] { new NamedFontSource("Times New Roman") },
		16, CommonFontWeights.NORMAL, new FontDecoration[0]);
	
	private SimpleDefaults() {}
	
}
