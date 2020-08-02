package everyos.engine.ribbon.renderer.guirenderer.directive;

import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIDirectiveWrapper;
import everyos.engine.ribbon.renderer.guirenderer.GUIComponentUI;
import everyos.engine.ribbon.renderer.guirenderer.graphics.Color;
import everyos.engine.ribbon.renderer.guirenderer.graphics.GUIConstants;

public interface BackgroundDirective extends UIDirective {
	public Color getBackground(); //TODO: `Texture` or `Paintbrush` instead
	public static UIDirectiveWrapper of(Color background) {
		return UIDirectiveWrapper.<BackgroundDirective>wrap(()->background, GUIConstants.PAINT, GUIComponentUI.class);
	}
}
