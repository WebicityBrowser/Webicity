package everyos.desktop.thready.core.gui.stage.paint;

import everyos.desktop.thready.core.graphics.ResourceGenerator;
import everyos.desktop.thready.core.gui.laf.animation.InvalidationScheduler;

public interface PaintContext {
	
	InvalidationScheduler getInvalidationScheduler();
	
	ResourceGenerator getResourceGenerator();
	
	int getMillisSinceLastFrame();
	
	void stepComposite();
	
}
