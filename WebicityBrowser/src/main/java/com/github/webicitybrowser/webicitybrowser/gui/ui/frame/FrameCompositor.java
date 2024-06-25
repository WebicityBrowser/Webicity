package com.github.webicitybrowser.webicitybrowser.gui.ui.frame;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeLayer.CompositeReference;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeParameters;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.GlobalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.LocalCompositeContext;

public final class FrameCompositor {
	
	private FrameCompositor() {}

	public static void composite(FrameUnit unit, GlobalCompositeContext globalCompositeContext, LocalCompositeContext localCompositeContext) {
		Rectangle documentRect = localCompositeContext.documentRect();
		CompositeParameters parameters = new CompositeParameters(CompositeReference.PARENT, () -> AbsolutePosition.ZERO_POSITION);
		LocalCompositeContext childCompositeContext = new LocalCompositeContext(new Rectangle(AbsolutePosition.ZERO_POSITION, documentRect.size()));
		globalCompositeContext.enterChildContext(documentRect, parameters);
		globalCompositeContext.addPaintUnit(unit, childCompositeContext);
		globalCompositeContext.exitChildContext();
	}
	
}
