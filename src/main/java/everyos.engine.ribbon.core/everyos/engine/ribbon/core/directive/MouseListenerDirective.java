package everyos.engine.ribbon.core.directive;

import everyos.engine.ribbon.core.event.EventListener;
import everyos.engine.ribbon.core.event.MouseEvent;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIDirectiveWrapper;

public interface MouseListenerDirective extends UIDirective {
	EventListener<MouseEvent> getListener();
	public static UIDirectiveWrapper of(EventListener<MouseEvent> listener) {
		return UIDirectiveWrapper.<MouseListenerDirective>wrap(()->listener, InvalidationLevel.IGNORE, ComponentUI.class);
	}
}
