package com.github.webicitybrowser.thready.gui.graphical.directive;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.graphical.event.mouse.MouseListener;

public interface ExternalMouseListenerDirective extends Directive {

	MouseListener getMouseListener();
	
	default Class<? extends Directive> getPrimaryType() {
		return ExternalMouseListenerDirective.class;
	}
	
	public static ExternalMouseListenerDirective of(MouseListener mouseListener) {
		return () -> mouseListener;
	}
	
}
