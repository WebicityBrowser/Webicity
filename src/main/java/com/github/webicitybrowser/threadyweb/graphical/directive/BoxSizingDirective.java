package com.github.webicitybrowser.threadyweb.graphical.directive;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface BoxSizingDirective extends Directive {
	
	BoxSizing getValue();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return BoxSizingDirective.class;
	}
	
	static BoxSizingDirective of(BoxSizing value) {
		return () -> value;
	}

	enum BoxSizing {
		CONTENT_BOX, BORDER_BOX;
	}

}
