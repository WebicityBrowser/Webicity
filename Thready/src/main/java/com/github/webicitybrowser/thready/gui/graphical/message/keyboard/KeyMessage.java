package com.github.webicitybrowser.thready.gui.graphical.message.keyboard;

import com.github.webicitybrowser.thready.windowing.core.event.keyboard.KeyScreenEvent;

public interface KeyMessage extends KeyboardMessage {

	KeyScreenEvent getScreenEvent();
	
}
