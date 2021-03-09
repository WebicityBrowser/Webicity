package everyos.engine.ribbon.renderer.guirenderer.directive;

import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIDirectiveWrapper;
import everyos.engine.ribbon.renderer.guirenderer.graphics.GUIConstants;
import everyos.engine.ribbon.renderer.guirenderer.shape.Location;

public interface PositionDirective extends UIDirective {
	public Location getPosition();
	public static UIDirectiveWrapper of(Location position) {
		return UIDirectiveWrapper.<PositionDirective>wrap(()->position, GUIConstants.RENDER, ComponentUI.class);
	}
}
