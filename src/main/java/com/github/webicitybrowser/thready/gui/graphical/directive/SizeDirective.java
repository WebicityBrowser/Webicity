package com.github.webicitybrowser.thready.gui.graphical.directive;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeSize;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface SizeDirective extends Directive {

	RelativeSize getSize();
	
	default Class<? extends Directive> getPrimaryType() {
		return SizeDirective.class;
	}
	
	public static SizeDirective of(RelativeSize size) {
		return () -> size;
	}
	
	public static SizeDirective of(AbsoluteSize size) {
		return () -> new RelativeSize(0, size.width(), 0, size.height());
	}

}
