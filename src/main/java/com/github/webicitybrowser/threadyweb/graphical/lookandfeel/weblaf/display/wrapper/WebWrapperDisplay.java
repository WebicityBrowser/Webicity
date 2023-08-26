package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.wrapper;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.message.NoopMessageHandler;

public class WebWrapperDisplay<T extends Context, U extends Box, V extends RenderedUnit> implements UIDisplay<WebWrapperContext<T>, Box, WebWrapperUnit<V>> {

	private final UIDisplay<T, U, V> innerDisplay;

	public WebWrapperDisplay(UIDisplay<T, U, V> innerDisplay) {
		this.innerDisplay = innerDisplay;
	}

	@Override
	public WebWrapperContext<T> createContext(ComponentUI componentUI) {
		return new WebWrapperContext<>(this, componentUI, innerDisplay.createContext(componentUI));
	}

	@Override
	public List<Box> generateBoxes(WebWrapperContext<T> displayContext, BoxContext boxContext, StyleGenerator styleGenerator) {
		DirectivePool styleDirectives = styleGenerator.getStyleDirectives();
		return WebWrapperBoxGenerator.generateBoxes(
			displayContext, styleDirectives,
			() -> UIPipeline.generateBoxes(displayContext.innerContext(), boxContext, styleGenerator));
	}

	@Override
	public WebWrapperUnit<V> renderBox(Box box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		if (box instanceof WebWrapperBox wrapperBox) {
			V innerUnit = UIPipeline.render(wrapperBox.innerBox(), renderContext, localRenderContext);

			return new WebWrapperUnit<>(this, wrapperBox, innerUnit);
		} else {
			throw new IllegalArgumentException("Box must be a WebWrapperBox");
		}
	}

	@Override
	public void paint(WebWrapperUnit<V> unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		WebWrapperPainter.paint(unit, globalPaintContext, localPaintContext);
	}

	@Override
	public MessageHandler createMessageHandler(WebWrapperUnit<V> unit, Rectangle documentRect) {
		return new NoopMessageHandler();
	}

}
