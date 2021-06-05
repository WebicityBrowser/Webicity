package everyos.engine.ribbon.core.directive;

import everyos.engine.ribbon.core.graphics.Color;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIDirectiveWrapper;

public interface BackgroundDirective extends UIDirective {
	public Color getBackground(); //TODO: `Texture` or `Paintbrush` instead
	public static UIDirectiveWrapper of(Color background) {
		return UIDirectiveWrapper.<BackgroundDirective>wrap(()->background, InvalidationLevel.PAINT, ComponentUI.class);
	}
}
