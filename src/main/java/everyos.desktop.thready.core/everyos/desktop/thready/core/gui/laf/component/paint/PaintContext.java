package everyos.desktop.thready.core.gui.laf.component.paint;

import everyos.desktop.thready.core.graphics.canvas.Canvas2D;
import everyos.desktop.thready.core.gui.laf.animation.InvalidationScheduler;

public interface PaintContext {
	
	Canvas2D getViewportCanvas();

	InvalidationScheduler getInvalidationScheduler();
	
	int getMillisSinceLastFrame();
	
	void stepComposite();
	
}
