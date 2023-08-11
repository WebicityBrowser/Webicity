package com.github.webicitybrowser.webicitybrowser.gui.ui.urlbar;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.view.textfield.TextFieldMessageHandler;
import com.github.webicitybrowser.thready.gui.graphical.viewmodel.textfield.TextFieldViewModel;
import com.github.webicitybrowser.thready.gui.message.Message;
import com.github.webicitybrowser.thready.gui.message.MessageContext;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.message.MessageResponse;

public class URLBarMessageHandler implements MessageHandler {

	private final TextFieldMessageHandler textFieldMessageHandler;

	public URLBarMessageHandler(Rectangle documentRect, Rectangle contentRect, URLBarUnit unit) {
		TextFieldViewModel textFieldViewModel = unit.context().textFieldContext().getViewModel();
		this.textFieldMessageHandler = new TextFieldMessageHandler(
			documentRect, contentRect,
			unit.box(), textFieldViewModel, unit.font());
	}

	@Override
	public MessageResponse onMessage(MessageContext messageContext, Message message) {
		return textFieldMessageHandler.onMessage(messageContext, message);
	}
	
}
