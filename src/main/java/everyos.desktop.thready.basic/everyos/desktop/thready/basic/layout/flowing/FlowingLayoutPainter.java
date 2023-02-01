package everyos.desktop.thready.basic.layout.flowing;

import everyos.desktop.thready.core.graphics.canvas.Canvas2D;
import everyos.desktop.thready.core.gui.stage.paint.PaintContext;
import everyos.desktop.thready.core.gui.stage.paint.Painter;
import everyos.desktop.thready.core.positioning.AbsolutePosition;
import everyos.desktop.thready.core.positioning.Rectangle;
import everyos.desktop.thready.core.positioning.imp.RectangleImp;
import everyos.desktop.thready.core.positioning.util.AbsolutePositionMath;

public class FlowingLayoutPainter implements Painter {

	private final Rectangle documentRect;
	private final FlowingLayoutResult[] resultsToPaint;

	public FlowingLayoutPainter(Rectangle documentRect, FlowingLayoutResult[] resultsToPaint) {
		this.documentRect = documentRect;
		this.resultsToPaint = resultsToPaint;
	}
	
	@Override
	public void paint(PaintContext context, Canvas2D canvas, Rectangle viewportRect) {
		for (FlowingLayoutResult resultToPaint: resultsToPaint) {
			paintResult(context, canvas, viewportRect, resultToPaint);
		}
	}

	private void paintResult(PaintContext context, Canvas2D canvas, Rectangle viewportRect, FlowingLayoutResult resultToPaint) {
		Rectangle resultDocumentRect = computeResultDocumentRect(resultToPaint.renderedRect());
		Painter resultPainter = resultToPaint.unit().getPainter(resultDocumentRect);
		// TODO: Pass properly clipped canvas and vprect
		// Also do culling
		resultPainter.paint(context, canvas, viewportRect);
	}

	private Rectangle computeResultDocumentRect(Rectangle renderedRect) {
		AbsolutePosition documentPosition = AbsolutePositionMath.sum(
			documentRect.getPosition(),
			renderedRect.getPosition());
		
		return new RectangleImp(documentPosition, renderedRect.getSize());
	}

}
