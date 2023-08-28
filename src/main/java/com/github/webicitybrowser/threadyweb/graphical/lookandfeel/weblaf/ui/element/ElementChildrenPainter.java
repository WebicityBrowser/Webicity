package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.util.AbsolutePositionMath;
import com.github.webicitybrowser.thready.dimensions.util.RectangleUtil;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;

public final class ElementChildrenPainter {

	private ElementChildrenPainter() {}
	
	public static void paintChildren(ElementUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		for (ChildLayoutResult childResult: unit.layoutResults().childLayoutResults()) {
			Rectangle childRect = computeResultDocumentRect(localPaintContext.documentRect(), childResult.relativeRect());
			if (childInViewport(childRect, localPaintContext.viewport())) {
				paintChild(globalPaintContext, localPaintContext, childResult, childRect);
			}
		}
	}

	private static void paintChild(GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext, ChildLayoutResult childLayoutResult, Rectangle childRect) {
		LocalPaintContext childPaintContext = new LocalPaintContext(
			localPaintContext.canvas(),
			childRect,
			localPaintContext.viewport());
		
		// TODO: Pass properly clipped canvas
		UIPipeline.paint(childLayoutResult.unit(), globalPaintContext, childPaintContext);
	}
	
	private static boolean childInViewport(Rectangle childRect, Rectangle viewport) {
		return RectangleUtil.intersects(childRect, viewport);
	}
	
	private static Rectangle computeResultDocumentRect(Rectangle documentRect, Rectangle renderedRect) {
		AbsolutePosition documentPosition = AbsolutePositionMath.sum(
			documentRect.position(),
			renderedRect.position());
		
		return new Rectangle(documentPosition, renderedRect.size());
	}

}
