package com.github.webicitybrowser.thready.gui.graphical.message;

import com.github.webicitybrowser.thready.gui.message.Message;
import com.github.webicitybrowser.thready.windowing.core.event.keyboard.CharScreenEvent;

public interface CharMessage extends Message {

	CharScreenEvent getScreenEvent();
	
}
