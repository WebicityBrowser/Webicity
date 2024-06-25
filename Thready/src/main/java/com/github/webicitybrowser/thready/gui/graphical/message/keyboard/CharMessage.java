package com.github.webicitybrowser.thready.gui.graphical.message.keyboard;

import com.github.webicitybrowser.thready.windowing.core.event.keyboard.CharScreenEvent;

public interface CharMessage extends KeyboardMessage {

	CharScreenEvent getScreenEvent();
	
}
