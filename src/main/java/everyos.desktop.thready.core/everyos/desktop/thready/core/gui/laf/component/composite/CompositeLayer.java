package everyos.desktop.thready.core.gui.laf.component.composite;

import everyos.desktop.thready.core.graphics.canvas.Canvas2D;
import everyos.desktop.thready.core.gui.laf.component.paint.PaintContext;
import everyos.desktop.thready.core.positioning.Rectangle;

public interface CompositeLayer {

	void setChildren(CompositeLayer[] children);
	
	void draw(PaintContext context, Canvas2D canvas, Rectangle viewportRect, int step);
	
	void resetSteps();
	
	void step();
	
}
