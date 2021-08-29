package everyos.engine.ribbon.core.directive;

import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIDirectiveWrapper;

public interface FontSizeDirective extends UIDirective {
	public int getSize();
	
	public static UIDirectiveWrapper of(int size) {
		return UIDirectiveWrapper.<FontSizeDirective>wrap(()->size, InvalidationLevel.RENDER, ComponentUI.class);
	}
}
