package everyos.engine.ribbon.renderer.guirenderer.directive;

import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIDirectiveWrapper;
import everyos.engine.ribbon.renderer.guirenderer.graphics.Color;
import everyos.engine.ribbon.renderer.guirenderer.graphics.GUIConstants;

public interface ForegroundDirective extends UIDirective {
	public Color getForeground(); //TODO: `Texture` or `Paintbrush` instead
	public static UIDirectiveWrapper of(Color foreground) {
		return UIDirectiveWrapper.<ForegroundDirective>wrap(()->foreground, GUIConstants.PAINT, ComponentUI.class);
	}
}
