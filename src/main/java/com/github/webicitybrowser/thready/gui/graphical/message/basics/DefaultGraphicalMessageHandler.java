package com.github.webicitybrowser.thready.gui.graphical.message.basics;

import java.util.Optional;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.message.basics.sub.MouseMessageHandler;
import com.github.webicitybrowser.thready.gui.message.Message;
import com.github.webicitybrowser.thready.gui.message.MessageContext;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.message.MessageResponse;

public class DefaultGraphicalMessageHandler implements MessageHandler {

	private final MessageHandler mouseMessageHandler;
	
	public DefaultGraphicalMessageHandler(Rectangle documentRect, Box box, MessageHandler... children) {
		this.mouseMessageHandler = new MouseMessageHandler(documentRect, Optional.ofNullable(box), children);
	}
	
	@Override
	public MessageResponse onMessage(MessageContext messageContext, Message message) {
		MessageResponse mouseMessageResponse = mouseMessageHandler.onMessage(messageContext, message);
		if (mouseMessageResponse != null) {
			return mouseMessageResponse;
		}
		
		return null;
	}

}
