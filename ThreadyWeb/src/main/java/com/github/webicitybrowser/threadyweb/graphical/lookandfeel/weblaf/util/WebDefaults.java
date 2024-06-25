package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util;

import com.github.webicitybrowser.thready.drawing.core.text.FontDecoration;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
import com.github.webicitybrowser.thready.drawing.core.text.CommonFontWeights;
import com.github.webicitybrowser.thready.drawing.core.text.source.FontSource;
import com.github.webicitybrowser.thready.drawing.core.text.source.NamedFontSource;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.ElementDisplay;

public final class WebDefaults {
	
	public static UIDisplay<?, ChildrenBox, ?> INLINE_DISPLAY = new ElementDisplay();

	public static FontSettings FONT = new FontSettings(
		new FontSource[] { new NamedFontSource("Times New Roman") },
		16, CommonFontWeights.NORMAL, new FontDecoration[0]);
	
	private WebDefaults() {}
	
}
