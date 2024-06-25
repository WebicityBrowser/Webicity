package com.github.webicitybrowser.thready.gui.directive.basics;

import java.util.List;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.tree.core.UINode;

public interface ChildrenDirective extends Directive {

	List<UINode> getChildren();
	
	default Class<? extends Directive> getPrimaryType() {
		return ChildrenDirective.class;
	}

	public static ChildrenDirective of(UINode...children) {
		return () -> List.of(children);
	}
	
}
