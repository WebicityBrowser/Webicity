package com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.position;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.PositionType;

public interface PositionTypeDirective extends Directive {
	
	PositionType getPositionType();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return PositionTypeDirective.class;
	}

	public static PositionTypeDirective of(PositionType positionType) {
		return () -> positionType;
	}
	
}
