package everyos.engine.ribbon.renderer.guirenderer.directive;

import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIDirectiveWrapper;
import everyos.engine.ribbon.renderer.guirenderer.event.MouseListener;
import everyos.engine.ribbon.renderer.guirenderer.graphics.GUIConstants;

public interface ExternalMouseListenerDirective extends UIDirective {
	public MouseListener getListener();
	public static UIDirectiveWrapper of(MouseListener listener) {
		return UIDirectiveWrapper.<ExternalMouseListenerDirective>wrap(()->listener, GUIConstants.IGNORE, ComponentUI.class);
	}
}
