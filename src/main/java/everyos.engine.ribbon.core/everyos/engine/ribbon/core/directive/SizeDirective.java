package everyos.engine.ribbon.core.directive;

import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.shape.Location;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIDirectiveWrapper;

public interface SizeDirective extends UIDirective {
	public Location getSize();
	
	public static UIDirectiveWrapper of(Location size) {
		return UIDirectiveWrapper.<SizeDirective>wrap(()->size, InvalidationLevel.RENDER, ComponentUI.class);
	}
}
