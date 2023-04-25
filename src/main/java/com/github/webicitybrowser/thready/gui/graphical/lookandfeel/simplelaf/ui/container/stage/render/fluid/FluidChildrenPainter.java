package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.stage.render.fluid;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.util.AbsolutePositionMath;
import com.github.webicitybrowser.thready.dimensions.util.RectangleUtil;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.PaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;

public class FluidChildrenPainter implements Painter {

	private final Rectangle documentRect;
	private final FluidChildrenResult[] renderResults;

	public FluidChildrenPainter(Rectangle documentRect, FluidChildrenResult[] renderResults) {
		this.documentRect = documentRect;
		this.renderResults = renderResults;
	}

	@Override
	public void paint(PaintContext context, Canvas2D canvas, Rectangle viewport) {
		for (FluidChildrenResult renderResult: renderResults) {
			if (childInViewport(renderResult, viewport)) {
				paintChild(renderResult, context, canvas, viewport);
			}
		}
	}

	private void paintChild(FluidChildrenResult renderResult, PaintContext context, Canvas2D canvas, Rectangle viewport) {
		AbsolutePosition childPosition = AbsolutePositionMath.sum(renderResult.position(), documentRect.position());
		Rectangle childDocumentRect = new Rectangle(childPosition, renderResult.unit().getMinimumSize());
		renderResult
			.unit()
			.getPainter(childDocumentRect)
			.paint(context, canvas, viewport);
	}
	
	private boolean childInViewport(FluidChildrenResult renderResult, Rectangle viewport) {
		Rectangle childRectangle = new Rectangle(renderResult.position(), renderResult.unit().getMinimumSize());
		return RectangleUtil.intersects(childRectangle, viewport);
	}

}
