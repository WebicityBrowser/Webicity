package com.github.webicitybrowser.webicitybrowser.gui.ui.tab;

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
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.webicitybrowser.gui.binding.component.tab.TabComponent;

public class TabDisplay implements UIDisplay<GenericContext, GenericBox<TabComponent, GenericContext>, TabUnit> {

	private static final TabDisplay INSTANCE = new TabDisplay();

	@Override
	public GenericContext createContext(ComponentUI componentUI) {
		return new GenericContext(this, componentUI);
	}

	@Override
	public List<GenericBox<TabComponent, GenericContext>> generateBoxes(GenericContext displayContext, BoxContext boxContext) {
		return List.of(new GenericBox<>(displayContext));
	}

	@Override
	public TabUnit renderBox(GenericBox<TabComponent, GenericContext> box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		return TabRenderer.render(box, renderContext, localRenderContext);
	}

	@Override
	public void paint(TabUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		TabPainter.paint(unit, localPaintContext);
	}

	@Override
	public MessageHandler createMessageHandler(TabUnit unit, Rectangle documentRect) {
		return new TabMessageHandler(documentRect, unit);
	}

	public static ComponentUI componentUI(Component component, ComponentUI parent) {
		return new GenericComponentUI(component, parent, INSTANCE);
	}

}
