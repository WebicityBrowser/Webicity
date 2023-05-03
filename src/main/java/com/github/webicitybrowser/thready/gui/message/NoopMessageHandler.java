package com.github.webicitybrowser.thready.gui.message;

public class NoopMessageHandler implements MessageHandler {

	@Override
	public MessageResponse onMessage(Message message) {
		return null;
	}

}
