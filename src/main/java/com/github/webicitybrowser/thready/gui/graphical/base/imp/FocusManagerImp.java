package com.github.webicitybrowser.thready.gui.graphical.base.imp;

import java.util.Optional;

import com.github.webicitybrowser.thready.gui.message.FocusManager;
import com.github.webicitybrowser.thready.gui.message.Message;
import com.github.webicitybrowser.thready.gui.message.MessageContext;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;

public class FocusManagerImp implements FocusManager {

	private Optional<MessageHandler> focused = Optional.empty();
	
	@Override
	public void setFocused(MessageHandler messageHandler) {
		focused = Optional.ofNullable(messageHandler);
	}

	@Override
	public void messageFocused(MessageContext context, Message message) {
		focused.ifPresent(handler -> handler.onMessage(context, message));
	}
	
}
