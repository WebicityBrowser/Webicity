package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.scroll;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.GlobalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.LocalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;

public class ScrollDisplay implements UIDisplay<ScrollContext, ScrollBox, ScrollUnit> {

	private final UIDisplay<?, ?, ?> innerDisplay;

	public ScrollDisplay(UIDisplay<?, ?, ?> innerDisplay) {
		this.innerDisplay = innerDisplay;
	}

	@Override
	public ScrollContext createContext(ComponentUI componentUI) {
		return new ScrollContext(this, componentUI, innerDisplay.createContext(componentUI));
	}

	@Override
	public List<ScrollBox> generateBoxes(ScrollContext displayContext, BoxContext boxContext) {
		List<Box> innerBoxes = UIPipeline.generateBoxes(displayContext.innerContext(), boxContext);
		if (innerBoxes.size() != 1) {
			throw new IllegalStateException("Multiple inner boxes for scroll display not yet supported");
		}
		Box innerBox = innerBoxes.get(0);

		return List.of(new ScrollBox(displayContext, displayContext.styleDirectives(), innerBox));
	}

	@Override
	public ScrollUnit renderBox(ScrollBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		return ScrollRenderer.renderBox(box, globalRenderContext, localRenderContext);
	}

	@Override
	public void composite(ScrollUnit unit, GlobalCompositeContext compositeContext, LocalCompositeContext localCompositeContext) {
		ScrollCompositor.composite(unit, compositeContext, localCompositeContext);
	}

	@Override
	public void paint(ScrollUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		ScrollPainter.paint(unit, globalPaintContext, localPaintContext);
	}

	@Override
	public MessageHandler createMessageHandler(ScrollUnit unit, Rectangle documentRect) {
		Rectangle childDocumentRect = new Rectangle(documentRect.position(), unit.innerUnitSize());
		MessageHandler innerMessageHandler = UIPipeline.createMessageHandler(unit.innerUnit(), childDocumentRect);
		return new ScrollbarMessageHandler(unit, documentRect, innerMessageHandler);
	}
	
}
