package com.github.webicitybrowser.webicitybrowser.gui.ui.tab;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleDirectiveUtil;

public final class TabRenderer {

	private TabRenderer() {}
	
	public static TabUnit render(TabBox box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		Font2D font = renderContext
			.resourceLoader()
			.loadFont(SimpleDirectiveUtil.getFontSettings(box.styleDirectives()));
		// TODO: Make sure preferred size is not unbounded
		return new TabUnit(box.display(), localRenderContext.preferredSize(), box, font, new TabButtonState());
	}

}
