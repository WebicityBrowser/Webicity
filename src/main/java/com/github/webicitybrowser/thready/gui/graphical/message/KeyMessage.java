package com.github.webicitybrowser.thready.gui.graphical.message;

import com.github.webicitybrowser.thready.gui.message.Message;
import com.github.webicitybrowser.thready.windowing.core.event.keyboard.KeyScreenEvent;

public interface KeyMessage extends Message {

	KeyScreenEvent getScreenEvent();
	
}
