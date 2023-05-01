package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.layout.flow.block.context.solid;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.util.AbsolutePositionMath;
import com.github.webicitybrowser.thready.dimensions.util.RectangleUtil;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.PaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;

public class FlowLayoutPainter implements Painter {
	
	private final Rectangle documentRect;
	private final FlowLayoutResult[] childrenResults;

	public FlowLayoutPainter(Rectangle documentRect, FlowLayoutResult[] childrenResults) {
		this.documentRect = documentRect;
		this.childrenResults = childrenResults;
	}

	@Override
	public void paint(PaintContext context, Canvas2D canvas, Rectangle viewport) {
		paintChildren(context, canvas, viewport);
	}

	private void paintChildren(PaintContext context, Canvas2D canvas, Rectangle viewport) {
		for (FlowLayoutResult childResult: childrenResults) {
			if (childInViewport(childResult, viewport)) {
				paintChild(context, canvas, viewport, childResult);
			}
		}
	}

	private void paintChild(PaintContext context, Canvas2D canvas, Rectangle viewport, FlowLayoutResult resultToPaint) {
		Rectangle resultDocumentRect = computeResultDocumentRect(resultToPaint.renderedRect());
		Painter resultPainter = resultToPaint.unit().getPainter(resultDocumentRect);
		// TODO: Pass properly clipped canvas
		resultPainter.paint(context, canvas, viewport);
	}
	
	private Rectangle computeResultDocumentRect(Rectangle renderedRect) {
		AbsolutePosition documentPosition = AbsolutePositionMath.sum(
			documentRect.position(),
			renderedRect.position());
		
		return new Rectangle(documentPosition, renderedRect.size());
	}
	
	private boolean childInViewport(FlowLayoutResult childResult, Rectangle viewport) {
		Rectangle childRectangle = childResult.renderedRect();
		return RectangleUtil.intersects(childRectangle, viewport);
	}

}
