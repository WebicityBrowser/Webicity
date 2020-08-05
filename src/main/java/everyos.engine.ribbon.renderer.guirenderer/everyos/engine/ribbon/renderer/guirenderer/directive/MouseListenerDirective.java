package everyos.engine.ribbon.renderer.guirenderer.directive;

import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIDirectiveWrapper;
import everyos.engine.ribbon.renderer.guirenderer.GUIComponentUI;
import everyos.engine.ribbon.renderer.guirenderer.event.MouseListener;
import everyos.engine.ribbon.renderer.guirenderer.graphics.GUIConstants;

public interface MouseListenerDirective extends UIDirective {
	public MouseListener getListener();
	public static UIDirectiveWrapper of(MouseListener listener) {
		return UIDirectiveWrapper.<MouseListenerDirective>wrap(()->listener, GUIConstants.IGNORE, GUIComponentUI.class);
	}
}
