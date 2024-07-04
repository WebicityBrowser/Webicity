package com.github.webicitybrowser.webicitybrowser.gui.ui.tab;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.GenericBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.context.GenericContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleDirectiveUtil;
import com.github.webicitybrowser.webicitybrowser.gui.binding.component.tab.TabComponent;

public final class TabRenderer {

	private TabRenderer() {}
	
	public static TabUnit render(GenericBox<TabComponent, GenericContext> box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		Font2D font = renderContext
			.resourceLoader()
			.loadFont(SimpleDirectiveUtil.getFontSettings(box.styleDirectives()));
		// TODO: Make sure preferred size is not unbounded
		return new TabUnit(localRenderContext.preferredSize(), box, font, new TabButtonState());
	}

}
