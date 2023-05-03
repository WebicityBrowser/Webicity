package com.github.webicitybrowser.thready.gui.graphical.message;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.gui.message.Message;

public interface MouseMessage extends Message {
	
	int getButton();
	
	int getAction();
	
	boolean isExternal();
	
	AbsolutePosition getViewportPosition();
	
	AbsolutePosition getScreenPosition();
	
}
