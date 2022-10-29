package everyos.desktop.thready.core.gui.laf.component.paint;

import everyos.desktop.thready.core.graphics.canvas.Canvas2D;

public interface WrappingUnitPainter {

	Canvas2D paintBefore(Canvas2D canvas);
	
	void paintAfter(Canvas2D canvas);
	
}
