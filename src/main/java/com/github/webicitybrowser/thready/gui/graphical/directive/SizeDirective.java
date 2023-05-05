package com.github.webicitybrowser.thready.gui.graphical.directive;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeSize;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;

public interface SizeDirective extends GraphicalDirective {

	RelativeSize getSize();
	
	@Override
	default Class<? extends Directive> getPrimaryType() {
		return SizeDirective.class;
	}
	
	@Override
	default InvalidationLevel getInvalidationLevel() {
		return InvalidationLevel.RENDER;
	};
	
	public static SizeDirective of(RelativeSize size) {
		return () -> size;
	}
	
	public static SizeDirective of(AbsoluteSize size) {
		return () -> new RelativeSize(0, size.width(), 0, size.height());
	}

}
