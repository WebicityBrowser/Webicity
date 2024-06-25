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
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;

public interface UIDisplay<T extends Context, U extends Box, V extends RenderedUnit> {

	T createContext(ComponentUI componentUI);
	
	List<U> generateBoxes(T displayContext, BoxContext boxContext);

	V renderBox(U box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext);

	default void composite(V unit, GlobalCompositeContext compositeContext, LocalCompositeContext localCompositeContext) {
		compositeContext.addPaintUnit(unit, localCompositeContext);
	};
	
	void paint(V unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext);

	MessageHandler createMessageHandler(V unit, Rectangle documentRect);
	
}
