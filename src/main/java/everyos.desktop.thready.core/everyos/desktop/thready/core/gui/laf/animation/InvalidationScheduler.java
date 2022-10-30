package everyos.desktop.thready.core.gui.laf.animation;

import everyos.desktop.thready.core.gui.InvalidationLevel;
import everyos.desktop.thready.core.gui.laf.component.ComponentUI;

public interface InvalidationScheduler {

	void scheduleInvalidationInMillis(int millis, ComponentUI ui, InvalidationLevel level);
	
	void scheduleInvalidationAtSystemMillis(int millis, ComponentUI ui, InvalidationLevel level);
	
	void scheduleInvalidationForNextFrame(ComponentUI ui, InvalidationLevel level);
	
}
