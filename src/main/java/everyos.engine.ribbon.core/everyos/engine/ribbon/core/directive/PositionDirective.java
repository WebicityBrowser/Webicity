package everyos.engine.ribbon.core.directive;

import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.shape.Location;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIDirectiveWrapper;

public interface PositionDirective extends UIDirective {
	public Location getPosition();
	public static UIDirectiveWrapper of(Location position) {
		return UIDirectiveWrapper.<PositionDirective>wrap(()->position, InvalidationLevel.RENDER, ComponentUI.class);
	}
}
