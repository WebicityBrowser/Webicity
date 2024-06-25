package com.github.webicitybrowser.webicitybrowser.gui.ui.urlbar;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleDirectiveUtil;

public final class URLBarRenderer {

	private URLBarRenderer() {}

	public static URLBarUnit render(URLBarBox box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext, UIDisplay<?, ?, ?> uiDisplay) {
		Font2D font = renderContext
			.resourceLoader()
			.loadFont(SimpleDirectiveUtil.getFontSettings(box.styleDirectives()));
		return new URLBarUnit(localRenderContext.preferredSize(), font, box);
	}

}
