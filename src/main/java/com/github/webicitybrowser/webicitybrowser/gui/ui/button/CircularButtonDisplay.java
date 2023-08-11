package com.github.webicitybrowser.webicitybrowser.gui.ui.button;

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
import com.github.webicitybrowser.webicitybrowser.component.CircularButtonComponent;

public class CircularButtonDisplay implements UIDisplay<CircularButtonContext, CircularButtonBox, CircularButtonUnit> {

	@Override
	public CircularButtonContext createContext(ComponentUI componentUI) {
		return new CircularButtonContext(componentUI);
	}

	@Override
	public List<CircularButtonBox> generateBoxes(CircularButtonContext displayContext, BoxContext boxContext, StyleGenerator styleGenerator) {
		CircularButtonComponent circularButtonComponent = (CircularButtonComponent) displayContext.componentUI().getComponent();
		DirectivePool styleDirectives = styleGenerator.getStyleDirectives();
		return List.of(new CircularButtonBox(circularButtonComponent, styleDirectives, this));
	}

	@Override
	public RenderedUnitGenerator<CircularButtonUnit> renderBox(CircularButtonBox box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		return new SingleRenderedUnitGenerator<>(CircularButtonRenderer.render(box, renderContext));
	}

	@Override
	public void paint(CircularButtonUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		CircularButtonPainter.paint(unit, localPaintContext);
	}

	@Override
	public MessageHandler createMessageHandler(CircularButtonUnit unit, Rectangle documentRect) {
		return new DefaultGraphicalMessageHandler(documentRect, unit.box());
	}

}
