package com.github.webicitybrowser.thready.gui.graphical.message.mouse;

import com.github.webicitybrowser.thready.gui.message.Message;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.ScrollScreenEvent;

public interface ScrollMessage extends Message {
	
	ScrollScreenEvent getScreenEvent();

}
