package com.github.webicitybrowser.webicitybrowser.gui.ui.menu;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.GenericBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.context.GenericContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleDirectiveUtil;
import com.github.webicitybrowser.webicitybrowser.component.MenuButtonComponent;

public final class MenuButtonRenderer {
	
	private MenuButtonRenderer() {}

	public static MenuButtonUnit render(
		GenericBox<MenuButtonComponent, GenericContext> box, GlobalRenderContext globalContext, LocalRenderContext localContext
	) {
		Font2D font = loadFont(box, globalContext);
		return new MenuButtonUnit(localContext.preferredSize(), box, font);
	}
	
	private static Font2D loadFont(GenericBox<MenuButtonComponent, GenericContext> box, GlobalRenderContext renderContext) {
		FontSettings fontInfo = SimpleDirectiveUtil.getFontSettings(box.styleDirectives());
		return renderContext
			.resourceLoader()
			.loadFont(fontInfo);
	}

}
