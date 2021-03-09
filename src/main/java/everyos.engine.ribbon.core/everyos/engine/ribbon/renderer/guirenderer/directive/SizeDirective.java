package everyos.engine.ribbon.renderer.guirenderer.directive;

import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIDirectiveWrapper;
import everyos.engine.ribbon.renderer.guirenderer.graphics.GUIConstants;
import everyos.engine.ribbon.renderer.guirenderer.shape.Location;

public interface SizeDirective extends UIDirective {
	public Location getSize();
	public static UIDirectiveWrapper of(Location size) {
		return UIDirectiveWrapper.<SizeDirective>wrap(()->size, GUIConstants.RENDER, ComponentUI.class);
	}
}
