package everyos.desktop.thready.laf.simple.util;

import everyos.desktop.thready.basic.directive.BackgroundColorDirective;
import everyos.desktop.thready.basic.directive.ChildrenDirective;
import everyos.desktop.thready.basic.directive.ForegroundColorDirective;
import everyos.desktop.thready.basic.directive.LayoutManagerDirective;
import everyos.desktop.thready.core.gui.InvalidationLevel;
import everyos.desktop.thready.core.gui.directive.Directive;

public final class InvalidationUtil {

	private InvalidationUtil() {}
	
	public static InvalidationLevel getInvalidationLevelFor(Class<? extends Directive> directiveCls) {
		if (directiveCls == BackgroundColorDirective.class) {
			return InvalidationLevel.PAINT;
		} else if (directiveCls == ForegroundColorDirective.class) {
			return InvalidationLevel.PAINT;
		} else if (directiveCls == LayoutManagerDirective.class) {
			return InvalidationLevel.BOX;
		} else if (directiveCls == ChildrenDirective.class) {
			return InvalidationLevel.BOX;
		}
		
		return InvalidationLevel.NONE;
	}
	
}
