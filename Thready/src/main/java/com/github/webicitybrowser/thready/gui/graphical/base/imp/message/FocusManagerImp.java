package com.github.webicitybrowser.thready.gui.graphical.base.imp.message;

import java.util.Optional;

import com.github.webicitybrowser.thready.gui.message.FocusChangeMessage;
import com.github.webicitybrowser.thready.gui.message.FocusManager;
import com.github.webicitybrowser.thready.gui.message.Message;
import com.github.webicitybrowser.thready.gui.message.MessageContext;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;

public class FocusManagerImp implements FocusManager {

	private Optional<MessageHandler> focused = Optional.empty();
	
	@Override
	public void setFocused(MessageHandler messageHandler, MessageContext context) {
		focused.ifPresent(handler -> handler.onMessage(context, createFocusMessage(false)));
		focused = Optional.ofNullable(messageHandler);
		focused.ifPresent(handler -> handler.onMessage(context, createFocusMessage(true)));
	}

	@Override
	public void messageFocused(MessageContext context, Message message) {
		focused.ifPresent(handler -> handler.onMessage(context, message));
	}

	@Override
	public void clearFocus() {
		focused = Optional.empty();
	}
	
	private FocusChangeMessage createFocusMessage(boolean isFocused) {
		return () -> isFocused;
	}
	
}
