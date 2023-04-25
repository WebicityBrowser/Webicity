package com.github.webicitybrowser.thready.gui.graphical.directive;

import com.github.webicitybrowser.thready.dimensions.RelativePosition;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface PositionDirective extends Directive {

	RelativePosition getPosition();
	
	default Class<? extends Directive> getPrimaryType() {
		return PositionDirective.class;
	}
	
	public static PositionDirective of(RelativePosition position) {
		return () -> position;
	}
	
}
