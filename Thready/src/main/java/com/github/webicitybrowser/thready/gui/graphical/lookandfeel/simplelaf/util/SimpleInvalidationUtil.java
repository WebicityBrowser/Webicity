package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util;

import com.github.webicitybrowser.thready.gui.directive.basics.ChildrenDirective;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.directive.GraphicalDirective;

public final class SimpleInvalidationUtil {

	private SimpleInvalidationUtil() {}

	public static InvalidationLevel getInvalidationLevelFor(Directive directive) {
		if (directive instanceof GraphicalDirective graphicalDirective) {
			return graphicalDirective.getInvalidationLevel();
		} else if (directive.getPrimaryType() == ChildrenDirective.class) {
			return InvalidationLevel.STYLE;
		} else {
			return InvalidationLevel.NONE;
		}
	}
	
}
