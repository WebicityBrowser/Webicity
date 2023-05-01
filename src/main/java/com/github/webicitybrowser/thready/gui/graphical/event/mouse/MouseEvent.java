package com.github.webicitybrowser.thready.gui.graphical.event.mouse;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public interface MouseEvent {

	Component getComponent();
	
	int getAction();
	
	int getButton();
	
	boolean isSource();
	
	boolean isExternal();
	
	AbsolutePosition getViewportPosition();
	
	AbsolutePosition getScreenPosition();
	
}
