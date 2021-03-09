package everyos.engine.ribbon.renderer.guirenderer.directive;

import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIDirectiveWrapper;
import everyos.engine.ribbon.renderer.guirenderer.graphics.GUIConstants;

public interface FontSizeDirective extends UIDirective {
	public int getSize();
	public static UIDirectiveWrapper of(int size) {
		return UIDirectiveWrapper.<FontSizeDirective>wrap(()->size, GUIConstants.RENDER, ComponentUI.class);
	}
}
