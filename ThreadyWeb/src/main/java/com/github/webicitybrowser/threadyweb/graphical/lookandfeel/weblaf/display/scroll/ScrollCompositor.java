package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.scroll;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeLayer.CompositeReference;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeParameters;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.GlobalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.LocalCompositeContext;

public final class ScrollCompositor {

	public static void composite(ScrollUnit unit, GlobalCompositeContext compositeContext, LocalCompositeContext localCompositeContext) {
		ScrollContext scrollContext = unit.box().scrollContext();
	
		compositeContext.addPaintUnit(unit, localCompositeContext);

		Rectangle compositeLayerRect = new Rectangle(
			localCompositeContext.documentRect().position(),
			unit.innerUnitSize());
		CompositeParameters compositeParameters = new CompositeParameters(CompositeReference.PARENT, () -> fixScrollPosition(scrollContext.scrollPosition()));
		compositeContext.enterChildContext(compositeLayerRect, compositeParameters);
		compositeChild(unit, compositeContext);
		compositeContext.exitChildContext();
	}

	private static void compositeChild(ScrollUnit unit, GlobalCompositeContext compositeContext) {
		Rectangle childDocumentRect = new Rectangle(
			AbsolutePosition.ZERO_POSITION,
			unit.innerUnitSize());
		LocalCompositeContext childLocalCompositeContext = new LocalCompositeContext(childDocumentRect);
		
		UIPipeline.composite(unit.innerUnit(), compositeContext, childLocalCompositeContext);
	}

	private static AbsolutePosition fixScrollPosition(AbsolutePosition scrollPosition) {
		return new AbsolutePosition(
			scrollPosition.x() == ScrollbarStyles.NOT_PRESENT ? 0 : scrollPosition.x(),
			scrollPosition.y() == ScrollbarStyles.NOT_PRESENT ? 0 : scrollPosition.y());
	}

}
