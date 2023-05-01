package com.github.webicitybrowser.thready.gui.graphical.directive;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.graphical.event.mouse.MouseListener;

public interface MouseListenerDirective extends Directive {

	MouseListener getMouseListener();
	
	default Class<? extends Directive> getPrimaryType() {
		return MouseListenerDirective.class;
	}
	
	public static MouseListenerDirective of(MouseListener mouseListener) {
		return () -> mouseListener;
	}
	
}
