package com.github.webicitybrowser.thready.gui.message;

public class NoopMessageHandler implements MessageHandler {

	@Override
	public MessageResponse onMessage(MessageContext messageContext, Message message) {
		return null;
	}

}
