package everyos.browser.webicitybrowser.gui.directive;

import everyos.browser.webicitybrowser.gui.Animation;
import everyos.browser.webicitybrowser.gui.ui.AnimatedComponentUI;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIDirectiveWrapper;

public interface AnimationDirective extends UIDirective {
	public Animation getAnimation();
	public static UIDirectiveWrapper of(Animation animation) {
		return UIDirectiveWrapper.<AnimationDirective>wrap(()->animation, InvalidationLevel.PAINT, AnimatedComponentUI.class);
	}
}