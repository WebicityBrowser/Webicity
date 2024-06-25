package com.github.webicitybrowser.thready.windowing.core.event.keyboard;

import com.github.webicitybrowser.thready.windowing.core.event.ScreenEvent;

public interface KeyScreenEvent extends ScreenEvent {

	String getName();
	
	int getAction();
	
	int getCode();
	
}
