package everyos.engine.ribbon.core.directive;

import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.graphics.font.FontInfo;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIDirectiveWrapper;

public interface FontInfoDirective extends UIDirective {
	
	public FontInfo getFontInfo();
	
	public static UIDirectiveWrapper of(FontInfo info) {
		return UIDirectiveWrapper.<FontInfoDirective>wrap(()->info, InvalidationLevel.RENDER, ComponentUI.class);
	}
	
}
