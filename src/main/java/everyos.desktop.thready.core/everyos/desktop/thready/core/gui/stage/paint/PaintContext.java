package everyos.desktop.thready.core.gui.stage.paint;

import everyos.desktop.thready.core.gui.laf.animation.InvalidationScheduler;

public interface PaintContext {
	
	InvalidationScheduler getInvalidationScheduler();
	
	int getMillisSinceLastFrame();
	
	void stepComposite();
	
}
