package com.github.webicitybrowser.webicitybrowser.gui.ui.tab;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.render.unit.SingleRenderedUnitGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;
import com.github.webicitybrowser.thready.gui.graphical.message.basics.DefaultGraphicalMessageHandler;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.webicitybrowser.gui.binding.component.tab.TabComponent;

public class TabDisplay implements UIDisplay<TabContext, TabBox, TabUnit> {

	@Override
	public TabContext createContext(ComponentUI componentUI) {
		return new TabContext(componentUI);
	}

	@Override
	public List<TabBox> generateBoxes(TabContext displayContext, BoxContext boxContext, StyleGenerator styleGenerator) {
		TabComponent owningComponent = (TabComponent) displayContext.componentUI().getComponent();
		DirectivePool styleDirectives = styleGenerator.getStyleDirectives();
		return List.of(new TabBox(owningComponent, styleDirectives, this));
	}

	@Override
	public RenderedUnitGenerator<TabUnit> renderBox(TabBox box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		TabUnit unit = TabRenderer.render(box, renderContext, localRenderContext);
		return new SingleRenderedUnitGenerator<>(unit);
	}

	@Override
	public void paint(TabUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		TabPainter.paint(unit, localPaintContext);
	}

	@Override
	public MessageHandler createMessageHandler(TabUnit unit, Rectangle documentRect) {
		return new DefaultGraphicalMessageHandler(documentRect, unit.box());
	}

}
