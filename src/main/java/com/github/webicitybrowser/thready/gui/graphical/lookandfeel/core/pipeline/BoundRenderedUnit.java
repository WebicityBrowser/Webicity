package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.imp.BoundRenderedUnitImp;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;

public interface BoundRenderedUnit<V extends RenderedUnit> {

	void paint(GlobalPaintContext globalPaintContext, LocalPaintContext childPaintContext);
	
	MessageHandler createMessageHandler(Rectangle documentRect);
	
	V getRaw();

	static <V extends RenderedUnit> BoundRenderedUnit<V> create(V unit, UIDisplay<?, ?, V> display) {
		return new BoundRenderedUnitImp<>(unit, display);
	}
	
}
