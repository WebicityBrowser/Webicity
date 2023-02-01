package everyos.desktop.thready.core.gui.stage.paint;

import everyos.desktop.thready.core.graphics.canvas.Canvas2D;
import everyos.desktop.thready.core.positioning.Rectangle;

public interface Painter {

	void paint(PaintContext context, Canvas2D canvas, Rectangle viewportRect);
	
}
