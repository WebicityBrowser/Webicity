package everyos.engine.ribbon.core.directive;

import everyos.engine.ribbon.core.event.MouseListener;
import everyos.engine.ribbon.core.graphics.GUIConstants;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIDirectiveWrapper;

public interface MouseListenerDirective extends UIDirective {
	public MouseListener getListener();
	public static UIDirectiveWrapper of(MouseListener listener) {
		return UIDirectiveWrapper.<MouseListenerDirective>wrap(()->listener, GUIConstants.IGNORE, ComponentUI.class);
	}
}
