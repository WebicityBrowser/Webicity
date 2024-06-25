package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.GlobalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.LocalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderCache;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;

public final class UIPipeline {

	private UIPipeline() {}

	@SuppressWarnings("unchecked")
	public static <T extends Context> List<Box> generateBoxes(Context context, BoxContext boxContext) {
		UIDisplay<T, ?, ?> display = (UIDisplay<T, ?, ?>) context.display();
		return (List<Box>) display.generateBoxes((T) context, boxContext);
	}

	@SuppressWarnings("unchecked")
	public static <U extends Box, V extends RenderedUnit> V render(
		Box box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext
	) {
		RenderCache renderCache = globalRenderContext.renderCache();
		V renderedUnit = (V) renderCache.get(box, localRenderContext.preferredSize());
		if (renderedUnit != null) return renderedUnit;

		UIDisplay<?, U, V> display = (UIDisplay<?, U, V>) box.display();
		V result = display.renderBox((U) box, globalRenderContext, localRenderContext);
		renderCache.put(box, localRenderContext.preferredSize(), result);
		renderCache.put(box, result.fitSize(), result);
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <V extends RenderedUnit> void composite(
		RenderedUnit unit, GlobalCompositeContext compositeContext, LocalCompositeContext localCompositeContext
	) {
		UIDisplay<?, ?, V> display = (UIDisplay<?, ?, V>) unit.display();
		display.composite((V) unit, compositeContext, localCompositeContext);
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
