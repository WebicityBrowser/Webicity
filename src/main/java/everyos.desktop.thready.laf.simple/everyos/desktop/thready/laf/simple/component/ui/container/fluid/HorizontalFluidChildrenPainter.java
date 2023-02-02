package everyos.desktop.thready.laf.simple.component.ui.container.fluid;

import everyos.desktop.thready.core.graphics.canvas.Canvas2D;
import everyos.desktop.thready.core.gui.stage.paint.PaintContext;
import everyos.desktop.thready.core.gui.stage.paint.Painter;
import everyos.desktop.thready.core.positioning.AbsolutePosition;
import everyos.desktop.thready.core.positioning.Rectangle;
import everyos.desktop.thready.core.positioning.imp.RectangleImp;
import everyos.desktop.thready.core.positioning.util.AbsolutePositionMath;

public class HorizontalFluidChildrenPainter implements Painter {

	private final Rectangle documentRect;
	private final FluidChildrenResult[] renderResults;

	public HorizontalFluidChildrenPainter(Rectangle documentRect, FluidChildrenResult[] renderResults) {
		this.documentRect = documentRect;
		this.renderResults = renderResults;
	}

	@Override
	public void paint(PaintContext context, Canvas2D canvas, Rectangle viewportRect) {
		for (FluidChildrenResult renderResult: renderResults) {
			paintChild(renderResult, context, canvas, viewportRect);
		}
	}

	private void paintChild(FluidChildrenResult renderResult, PaintContext context, Canvas2D canvas, Rectangle viewportRect) {
		AbsolutePosition childPosition = AbsolutePositionMath.sum(renderResult.position(), documentRect.getPosition());
		Rectangle childDocumentRect = new RectangleImp(childPosition, renderResult.unit().getMinimumSize());
		renderResult
			.unit()
			.getPainter(childDocumentRect)
			.paint(context, canvas, viewportRect);
	}

}
