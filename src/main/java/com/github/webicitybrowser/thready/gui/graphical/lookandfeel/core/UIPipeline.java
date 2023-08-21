package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;

public final class UIPipeline {

	private UIPipeline() {}

	@SuppressWarnings("unchecked")
	public static <T extends Context> List<Box> generateBoxes(
		Context context, BoxContext boxContext, StyleGenerator styleGenerator
	) {
		UIDisplay<T, ?, ?> display = (UIDisplay<T, ?, ?>) context.display();
		return (List<Box>) display.generateBoxes((T) context, boxContext, styleGenerator);
	}

	@SuppressWarnings("unchecked")
	public static <U extends Box, V extends RenderedUnit> RenderedUnitGenerator<V> render(
		Box box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext
	) {
		UIDisplay<?, U, V> display = (UIDisplay<?, U, V>) box.display();
		return display.renderBox((U) box, globalRenderContext, localRenderContext);
	}

	@SuppressWarnings("unchecked")
	public static <V extends RenderedUnit> void paint(
		RenderedUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext childPaintContext
	) {
		UIDisplay<?, ?, V> display = (UIDisplay<?, ?, V>) unit.display();
		display.paint((V) unit, globalPaintContext, childPaintContext);
	}

	@SuppressWarnings("unchecked")
	public static <V extends RenderedUnit> MessageHandler createMessageHandler(RenderedUnit unit, Rectangle childDocumentRect) {
		UIDisplay<?, ?, V> display = (UIDisplay<?, ?, V>) unit.display();
		return display.createMessageHandler((V) unit, childDocumentRect);
	}
	
}
