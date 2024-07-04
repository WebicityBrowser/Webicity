package com.github.webicitybrowser.webicitybrowser.gui.ui.menu;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.GenericComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.GenericBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.context.GenericContext;
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
import com.github.webicitybrowser.webicitybrowser.component.MenuButtonComponent;

public class MenuButtonDisplay implements UIDisplay<GenericContext, GenericBox<MenuButtonComponent, GenericContext>, MenuButtonUnit> {

	private static final MenuButtonDisplay INSTANCE = new MenuButtonDisplay();

	@Override
	public GenericContext createContext(ComponentUI componentUI) {
		return new GenericContext(this, componentUI);
	}

	@Override
	public List<GenericBox<MenuButtonComponent, GenericContext>> generateBoxes(GenericContext displayContext, BoxContext boxContext) {
		return List.of(new GenericBox<>(displayContext));
	}

	@Override
	public MenuButtonUnit renderBox(GenericBox<MenuButtonComponent, GenericContext> box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
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

	public static ComponentUI componentUI(Component component, ComponentUI parent) {
		return new GenericComponentUI(component, parent, INSTANCE);
	}

}
