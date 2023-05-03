package com.github.webicitybrowser.thready.windowing.core.event.mouse;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.windowing.core.event.ScreenEvent;

public interface MouseScreenEvent extends ScreenEvent {

	int getAction();
	
	int getButton();
	
	AbsolutePosition getViewportPosition();
	
	AbsolutePosition getScreenPosition();
	
}
