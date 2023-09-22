package com.github.webicitybrowser.thready.gui.message;

public interface FocusManager {

	void setFocused(MessageHandler messageHandler, MessageContext context);
	
	void messageFocused(MessageContext context, Message message);

	void clearFocus();
	
}
