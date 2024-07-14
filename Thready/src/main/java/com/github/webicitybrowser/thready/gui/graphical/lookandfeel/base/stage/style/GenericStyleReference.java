package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.style;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleReference;

public class GenericStyleReference<T extends Directive> implements StyleReference<T> {

	private final Class<T> directiveClass;

	private T directive;

	public GenericStyleReference(Class<T> directiveClass) {
		this.directiveClass = directiveClass;
	}

	@Override
	public T get() {
		if (directive == null) {
			throw new IllegalStateException("Directive not set");
		}

		return directive;
	}

	public void set(T directive) {
		this.directive = directive;
	}

	public Class<T> getDirectiveClass() {
		return directiveClass;
	}
	
}
