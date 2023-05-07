package com.github.webicitybrowser.thready.gui.graphical.message.mouse;

import com.github.webicitybrowser.thready.gui.message.Message;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.MouseScreenEvent;

public interface MouseMessage extends Message {
	
	MouseScreenEvent getScreenEvent();
	
	boolean isExternal();
	
}
