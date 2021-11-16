package everyos.engine.ribbon.core.rendering;

import everyos.engine.ribbon.core.event.UIEventTarget;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.util.TimeSystem;

public interface SharedRendererContext {

	TimeSystem getTimeSystem();
	void delayInvalidate(long millis, InvalidationLevel level);
	void setFocusedEventTarget(UIEventTarget target);
	UIEventTarget getFocusedEventTarget();
	
}
