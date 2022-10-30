package everyos.desktop.thready.core.gui.laf.component.paint;

import everyos.desktop.thready.core.graphics.canvas.Canvas2D;

public interface WrappingUnitPainter {

	Canvas2D paintBefore(PaintContext context, Canvas2D canvas);
	
	void paintAfter(PaintContext context, Canvas2D canvas);
	
}
