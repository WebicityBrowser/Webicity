package com.github.webicitybrowser.thready.windowing.core.event.mouse;

import com.github.webicitybrowser.thready.windowing.core.event.ScreenEvent;

public interface ScrollScreenEvent extends ScreenEvent {
	
	float getScrollX();

	float getScrollY();

}
