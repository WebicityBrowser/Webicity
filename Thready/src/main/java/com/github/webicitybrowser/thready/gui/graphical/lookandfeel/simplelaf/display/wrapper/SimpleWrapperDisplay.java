package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.display.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
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

public class SimpleWrapperDisplay<T extends Context, U extends Box, V extends RenderedUnit> implements UIDisplay<SimpleWrapperContext<T>, SimpleWrapperBox<U>, SimpleWrapperUnit<V>> {

	private final UIDisplay<T, U, V> childDisplay;

	public SimpleWrapperDisplay(UIDisplay<T, U, V> childDisplay) {
		this.childDisplay = childDisplay;
	}
	
	@Override
	public SimpleWrapperContext<T> createContext(ComponentUI componentUI) {
		return new SimpleWrapperContext<>(this, componentUI, childDisplay.createContext(componentUI));
	}

	@Override
	public List<SimpleWrapperBox<U>> generateBoxes(SimpleWrapperContext<T> displayContext, BoxContext boxContext) {
		List<U> originalBoxes = childDisplay.generateBoxes(displayContext.childContext(), boxContext);
		List<SimpleWrapperBox<U>> wrappedBoxes = new ArrayList<>(originalBoxes.size());
		for (U originalBox: originalBoxes) {
			wrappedBoxes.add(new SimpleWrapperBox<>(this, originalBox));
		}
		
		return wrappedBoxes;
	}

	@Override
	public SimpleWrapperUnit<V> renderBox(SimpleWrapperBox<U> box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		V childUnit = childDisplay.renderBox(box.innerBox(), renderContext, localRenderContext);
		return new SimpleWrapperUnit<>(this, childUnit.fitSize(), childUnit);
	}

	@Override
	public void composite(SimpleWrapperUnit<V> unit, GlobalCompositeContext compositeContext, LocalCompositeContext localCompositeContext) {
		SimpleWrapperCompositor.<V>composite(unit, childDisplay, compositeContext, localCompositeContext);
	}

	@Override
	public void paint(SimpleWrapperUnit<V> unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		SimpleWrapperPainter.<V>paint(unit, childDisplay, globalPaintContext, localPaintContext);
	}

	@Override
	public MessageHandler createMessageHandler(SimpleWrapperUnit<V> unit, Rectangle documentRect) {
		return childDisplay.createMessageHandler(unit.childUnit(), documentRect);
	}

}
