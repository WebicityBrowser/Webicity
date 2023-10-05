package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.util.AbsoluteDimensionsMath;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.GlobalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.LocalCompositeContext;

public final class ContainerCompositor {

	private ContainerCompositor() {}

	public static void composite(ContainerRenderedUnit unit, GlobalCompositeContext compositeContext, LocalCompositeContext localCompositeContext) {
		compositeContext.addPaintUnit(unit, localCompositeContext);
		compositeChildren(unit, compositeContext, localCompositeContext);
	}

	private static void compositeChildren(ContainerRenderedUnit unit, GlobalCompositeContext compositeContext, LocalCompositeContext localCompositeContext) {
		for (ChildLayoutResult childResult: unit.childLayoutResults()) {
			compositeChild(childResult, compositeContext, localCompositeContext);
		}
	}

	private static void compositeChild(ChildLayoutResult resultToPaint, GlobalCompositeContext compositeContext, LocalCompositeContext localCompositeContext) {
		LocalCompositeContext childPaintContext = createChildCompositeContext(resultToPaint, localCompositeContext);
		UIPipeline.composite(resultToPaint.unit(), compositeContext, childPaintContext);
	}
	
	private static LocalCompositeContext createChildCompositeContext(ChildLayoutResult resultToPaint, LocalCompositeContext localCompositeContext) {
		Rectangle resultDocumentRect = computeResultDocumentRect(localCompositeContext.documentRect(), resultToPaint.relativeRect());
		return new LocalCompositeContext(resultDocumentRect);
	}

	private static Rectangle computeResultDocumentRect(Rectangle documentRect, Rectangle renderedRect) {
		AbsolutePosition documentPosition = AbsoluteDimensionsMath.sum(
			documentRect.position(), renderedRect.position(), AbsolutePosition::new);
		
		return new Rectangle(documentPosition, renderedRect.size());
	}

}
