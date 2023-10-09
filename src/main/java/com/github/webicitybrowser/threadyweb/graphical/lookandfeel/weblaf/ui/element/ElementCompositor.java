package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.util.AbsoluteDimensionsMath;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.GlobalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.LocalCompositeContext;

public final class ElementCompositor {

	private ElementCompositor() {}

	public static void composite(ElementUnit unit, GlobalCompositeContext globalCompositeContext, LocalCompositeContext localCompositeContext) {
		globalCompositeContext.addPaintUnit(unit, localCompositeContext);
		compositeChildren(unit, globalCompositeContext, localCompositeContext);
	}
	
	private static void compositeChildren(ElementUnit unit, GlobalCompositeContext globalCompositeContext, LocalCompositeContext localCompositeContext) {
		for (ChildLayoutResult childResult: unit.layoutResults().childLayoutResults()) {
			Rectangle childRect = computeResultDocumentRect(localCompositeContext.documentRect(), childResult.relativeRect());
			compositeChild(globalCompositeContext, localCompositeContext, childResult, childRect);
		}
	}

	private static void compositeChild(
		GlobalCompositeContext globalCompositeContext, LocalCompositeContext localCompositeContext,
		ChildLayoutResult childLayoutResult, Rectangle childRect
	) {
		LocalCompositeContext childCompositeContext = new LocalCompositeContext(childRect);
		UIPipeline.composite(childLayoutResult.unit(), globalCompositeContext, childCompositeContext);
	}
	
	private static Rectangle computeResultDocumentRect(Rectangle documentRect, Rectangle renderedRect) {
		AbsolutePosition documentPosition = AbsoluteDimensionsMath.sum(
			documentRect.position(), renderedRect.position(), AbsolutePosition::new);
		
		return new Rectangle(documentPosition, renderedRect.size());
	}

}
