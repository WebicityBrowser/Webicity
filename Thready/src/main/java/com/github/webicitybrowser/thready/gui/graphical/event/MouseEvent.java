package com.github.webicitybrowser.thready.gui.graphical.event;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.gui.event.Event;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public interface MouseEvent extends Event {

	int getAction();
	
	int getButton();
	
	AbsolutePosition getViewportPosition();
	
	AbsolutePosition getScreenPosition();
	
	boolean isExternal();
	
	boolean isSource();
	
	Component getComponent();
	
}
