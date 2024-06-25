package com.github.webicitybrowser.webicitybrowser.gui.ui.menu;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.message.basics.DefaultGraphicalMessageHandler;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class MenuButtonDisplay implements UIDisplay<MenuButtonContext, MenuButtonBox, MenuButtonUnit> {

	@Override
	public MenuButtonContext createContext(ComponentUI componentUI) {
		return new MenuButtonContext(this, componentUI);
	}

	@Override
	public List<MenuButtonBox> generateBoxes(MenuButtonContext displayContext, BoxContext boxContext) {
		Component owningComponent = displayContext.componentUI().getComponent();
		DirectivePool styleDirectives = displayContext.styleDirectives();
		return List.of(new MenuButtonBox(owningComponent, styleDirectives, this));
	}

	@Override
	public MenuButtonUnit renderBox(MenuButtonBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		return MenuButtonRenderer.render(box, globalRenderContext, localRenderContext);
	}

	@Override
	public void paint(MenuButtonUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		MenuButtonPainter.paint(unit, localPaintContext);
	}

	@Override
	public MessageHandler createMessageHandler(MenuButtonUnit unit, Rectangle documentRect) {
		return new DefaultGraphicalMessageHandler(documentRect, unit.box());
	}

}
