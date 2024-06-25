package com.github.webicitybrowser.thready.gui.graphical.animation;

import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;

public interface InvalidationScheduler {

	void scheduleInvalidationInMillis(long millis, ComponentUI ui, InvalidationLevel level);
	
	void scheduleInvalidationAtSystemMillis(long millis, ComponentUI ui, InvalidationLevel level);
	
	void scheduleInvalidationForNextFrame(ComponentUI ui, InvalidationLevel level);
	
}
