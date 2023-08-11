package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.imp;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundRenderedUnit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;

public class BoundRenderedUnitImp<V extends RenderedUnit> implements BoundRenderedUnit<V> {

	private final V unit;
	private final UIDisplay<?, ?, V> display;

	public BoundRenderedUnitImp(V unit, UIDisplay<?, ?, V> display) {
		this.unit = unit;
		this.display = display;
	}
	
	@Override
	public void paint(GlobalPaintContext globalPaintContext, LocalPaintContext childPaintContext) {
		display.paint(unit, globalPaintContext, childPaintContext);
	}

	@Override
	public MessageHandler createMessageHandler(Rectangle documentRect) {
		return display.createMessageHandler(unit, documentRect);
	}

	@Override
	public V getRaw() {
		return this.unit;
	}

}
