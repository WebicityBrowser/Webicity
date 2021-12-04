package everyos.engine.ribbon.components.directive;

import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.graphics.paintfill.PaintFill;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIDirectiveWrapper;

public interface ForegroundDirective extends UIDirective {
	
	public PaintFill getForeground(); //TODO: `Texture` or `Paintbrush` instead
	
	public static UIDirectiveWrapper of(PaintFill foreground) {
		return UIDirectiveWrapper.<ForegroundDirective>wrap(()->foreground, InvalidationLevel.PAINT, ComponentUI.class);
	}
	
}
