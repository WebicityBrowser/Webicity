package com.github.webicitybrowser.thready.windowing.core.event.keyboard;

import com.github.webicitybrowser.thready.windowing.core.event.ScreenEvent;

public interface CharScreenEvent extends ScreenEvent {

	int getCodepoint();
	
}
