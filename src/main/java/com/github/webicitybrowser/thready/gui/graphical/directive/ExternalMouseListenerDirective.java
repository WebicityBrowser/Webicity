package com.github.webicitybrowser.thready.gui.graphical.directive;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.MouseListener;

public interface ExternalMouseListenerDirective extends GraphicalDirective {

	MouseListener getMouseListener();
	
	@Override
	default Class<? extends Directive> getPrimaryType() {
		return ExternalMouseListenerDirective.class;
	}
	
	@Override
	default InvalidationLevel getInvalidationLevel() {
		return InvalidationLevel.NONE;
	};
	
	public static ExternalMouseListenerDirective of(MouseListener mouseListener) {
		return () -> mouseListener;
	}
	
}
