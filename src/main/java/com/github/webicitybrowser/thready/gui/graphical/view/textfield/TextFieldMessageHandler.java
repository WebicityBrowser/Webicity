package com.github.webicitybrowser.thready.gui.graphical.view.textfield;

import java.util.Optional;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.event.MouseEvent;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.message.basics.sub.MouseMessageHandler;
import com.github.webicitybrowser.thready.gui.graphical.message.keyboard.KeyboardMessage;
import com.github.webicitybrowser.thready.gui.graphical.message.mouse.MouseMessage;
import com.github.webicitybrowser.thready.gui.graphical.viewmodel.textfield.TextFieldViewModel;
import com.github.webicitybrowser.thready.gui.message.FocusChangeMessage;
import com.github.webicitybrowser.thready.gui.message.Message;
import com.github.webicitybrowser.thready.gui.message.MessageContext;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.message.MessageResponse;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.MouseConstants;

public class TextFieldMessageHandler implements MessageHandler {

	private final MouseMessageHandler mouseMessageHandler;
	private final TextFieldKeyboardMessageHandler keyboardMessageHandler;
	private final TextFieldViewModel textFieldViewModel;

	public TextFieldMessageHandler(Rectangle documentRect, Rectangle contentRect, Box box, TextFieldViewModel textFieldViewModel) {
		this.mouseMessageHandler = createMouseMessageHandler(documentRect, box);
		this.keyboardMessageHandler = new TextFieldKeyboardMessageHandler(textFieldViewModel);
		this.textFieldViewModel = textFieldViewModel;
	}
	
	@Override
	public MessageResponse onMessage(MessageContext messageContext, Message message) {
		if (message instanceof MouseMessage) {
			return handleMouseMessage(messageContext, message);
		} else if (message instanceof KeyboardMessage) {
			return handleKeyboardMessage(messageContext, message);
		} else if (message instanceof FocusChangeMessage focusMessage) {
			textFieldViewModel.setFocused(focusMessage.isFocused());
		}
		
		return null;
	}

	private MessageResponse handleMouseMessage(MessageContext messageContext, Message message) {
		return mouseMessageHandler.onMessage(messageContext, message);
	}
	
	private MessageResponse handleKeyboardMessage(MessageContext messageContext, Message message) {
		return keyboardMessageHandler.onMessage(messageContext, message);
	}

	private MouseMessageHandler createMouseMessageHandler(Rectangle documentRect, Box box) {
		MessageHandler mainHandler = this;
		return new MouseMessageHandler(documentRect, Optional.ofNullable(box)) {
			@Override
			protected void onInternalMouseEvent(MessageContext messageContext, MouseEvent mouseEvent) {
				if (mouseEvent.getButton() == MouseConstants.LEFT_BUTTON && mouseEvent.getAction() == MouseConstants.PRESS) {
					messageContext.getFocusManager().setFocused(mainHandler, messageContext);
				}
			}
		};
	}

}
