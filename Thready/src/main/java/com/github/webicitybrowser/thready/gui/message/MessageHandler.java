package com.github.webicitybrowser.thready.gui.message;

public interface MessageHandler {

	MessageResponse onMessage(MessageContext context, Message message);
	
}
