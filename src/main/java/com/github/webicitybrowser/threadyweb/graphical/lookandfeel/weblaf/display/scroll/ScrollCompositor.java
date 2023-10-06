package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.scroll;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeLayer.CompositeReference;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.GlobalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.LocalCompositeContext;

public final class ScrollCompositor {

	public static void composite(ScrollUnit unit, GlobalCompositeContext compositeContext, LocalCompositeContext localCompositeContext) {
		compositeContext.addPaintUnit(unit, localCompositeContext);

		Rectangle innerRect = localCompositeContext.documentRect();
		compositeContext.enterChildContext(innerRect, CompositeReference.PAGE);
		compositeChild(unit, compositeContext, localCompositeContext);
		compositeContext.exitChildContext();
	}

	private static void compositeChild(ScrollUnit unit, GlobalCompositeContext compositeContext, LocalCompositeContext localCompositeContext) {
		Rectangle childDocumentRect = new Rectangle(
			new AbsolutePosition(0, 0),
			unit.innerUnitSize());
		LocalCompositeContext childLocalCompositeContext = new LocalCompositeContext(childDocumentRect);
		
		UIPipeline.composite(unit.innerUnit(), compositeContext, childLocalCompositeContext);
	}

}
