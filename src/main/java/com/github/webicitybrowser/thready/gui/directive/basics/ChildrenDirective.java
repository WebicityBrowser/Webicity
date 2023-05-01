package com.github.webicitybrowser.thready.gui.directive.basics;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.tree.core.UINode;

public interface ChildrenDirective extends Directive {

	UINode[] getChildren();
	
	default Class<? extends Directive> getPrimaryType() {
		return ChildrenDirective.class;
	}

	public static ChildrenDirective of(UINode...children) {
		return () -> children;
	}
	
}
