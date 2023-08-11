package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.util.AbsolutePositionMath;
import com.github.webicitybrowser.thready.dimensions.util.RectangleUtil;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;

public final class ContainerPainter {
	
	private ContainerPainter() {}
	
	public static void paint(ContainerRenderedUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		paintChildren(unit, globalPaintContext, localPaintContext);
	}

	private static void paintChildren(ContainerRenderedUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		for (ChildLayoutResult childResult: unit.childLayoutResults()) {
			Rectangle childDocumentRect = computeResultDocumentRect(localPaintContext.documentRect(), childResult.relativeRect());
			if (childInViewport(localPaintContext.viewport(), childDocumentRect)) {
				paintChild(childResult, globalPaintContext, localPaintContext);
			}
		}
	}

	private static void paintChild(ChildLayoutResult resultToPaint, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		LocalPaintContext childPaintContext = createChildPaintContext(resultToPaint, localPaintContext);
		resultToPaint.unit().paint(globalPaintContext, childPaintContext);
	}
	
	private static LocalPaintContext createChildPaintContext(ChildLayoutResult resultToPaint, LocalPaintContext localPaintContext) {
		Rectangle resultDocumentRect = computeResultDocumentRect(localPaintContext.documentRect(), resultToPaint.relativeRect());
		// TODO: Pass properly clipped canvas
		return new LocalPaintContext(localPaintContext.canvas(), resultDocumentRect, localPaintContext.viewport());
	}

	private static Rectangle computeResultDocumentRect(Rectangle documentRect, Rectangle renderedRect) {
		AbsolutePosition documentPosition = AbsolutePositionMath.sum(
			documentRect.position(),
			renderedRect.position());
		
		return new Rectangle(documentPosition, renderedRect.size());
	}
	
	private static boolean childInViewport(Rectangle viewport, Rectangle childRectangle) {
		return RectangleUtil.intersects(childRectangle, viewport);
	}

}
