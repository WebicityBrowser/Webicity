package com.github.webicitybrowser.thready.gui.message;

public interface FocusManager {

	void setFocused(MessageHandler messageHandler);
	
	void messageFocused(MessageContext context, Message message);
	
}
