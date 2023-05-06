package com.github.webicitybrowser.thready.gui.graphical.layout.base.flowing.imp;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.message.basics.DefaultGraphicalMessageHandler;
import com.github.webicitybrowser.thready.gui.message.Message;
import com.github.webicitybrowser.thready.gui.message.MessageContext;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.message.MessageResponse;

public class FlowingLayoutMessageHandler implements MessageHandler {

	private final MessageHandler mouseMessageHandler;
	
	public FlowingLayoutMessageHandler(Rectangle documentRect, MessageHandler... children) {
		this.mouseMessageHandler = new DefaultGraphicalMessageHandler(documentRect, null, children);
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