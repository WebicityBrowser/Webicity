package everyos.desktop.thready.core.gui.laf.component.paint;

import everyos.desktop.thready.core.graphics.canvas.Canvas2D;
import everyos.desktop.thready.core.positioning.Rectangle;

public interface ContentUnitPainter {

	void paint(PaintContext context, Canvas2D canvas, Rectangle viewportRect);
	
}
