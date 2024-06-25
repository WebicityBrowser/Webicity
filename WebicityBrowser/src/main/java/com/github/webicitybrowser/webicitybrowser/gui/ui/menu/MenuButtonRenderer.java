package com.github.webicitybrowser.webicitybrowser.gui.ui.menu;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleDirectiveUtil;

public final class MenuButtonRenderer {
	
	private MenuButtonRenderer() {}

	public static MenuButtonUnit render(MenuButtonBox box, GlobalRenderContext globalContext, LocalRenderContext localContext) {
		Font2D font = loadFont(box, globalContext);
		return new MenuButtonUnit(localContext.preferredSize(), box, font);
	}
	
	private static Font2D loadFont(MenuButtonBox box, GlobalRenderContext renderContext) {
		FontSettings fontInfo = SimpleDirectiveUtil.getFontSettings(box.styleDirectives());
		return renderContext
			.resourceLoader()
			.loadFont(fontInfo);
	}

}
