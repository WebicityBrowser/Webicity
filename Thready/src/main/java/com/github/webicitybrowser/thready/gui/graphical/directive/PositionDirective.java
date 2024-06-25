package com.github.webicitybrowser.thready.gui.graphical.directive;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.RelativePosition;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;

public interface PositionDirective extends GraphicalDirective {

	RelativePosition getPosition();
	
	@Override
	default Class<? extends Directive> getPrimaryType() {
		return PositionDirective.class;
	}
	
	@Override
	default InvalidationLevel getInvalidationLevel() {
		return InvalidationLevel.RENDER;
	};
	
	public static PositionDirective of(RelativePosition position) {
		return () -> position;
	}

	public static PositionDirective of(AbsolutePosition position) {
		return () -> new RelativePosition(0, position.x(), 0, position.y());
	}
	
}
