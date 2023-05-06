package com.github.webicitybrowser.webicitybrowser.gui.ui.urlbar;

import java.util.Optional;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.event.MouseEvent;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.message.CharMessage;
import com.github.webicitybrowser.thready.gui.graphical.message.KeyMessage;
import com.github.webicitybrowser.thready.gui.graphical.message.MouseMessage;
import com.github.webicitybrowser.thready.gui.graphical.message.basics.sub.MouseMessageHandler;
import com.github.webicitybrowser.thready.gui.message.Message;
import com.github.webicitybrowser.thready.gui.message.MessageContext;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.message.MessageResponse;
import com.github.webicitybrowser.thready.model.textfield.TextFieldModel;
import com.github.webicitybrowser.thready.windowing.core.event.keyboard.KeyboardConstants;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.MouseConstants;

public class URLBarMessageHandler implements MessageHandler {

	private final MouseMessageHandler mouseMessageHandler;
	private final TextFieldModel textFieldModel;

	public URLBarMessageHandler(Rectangle documentRect, Box box, TextFieldModel textFieldModel) {
		this.mouseMessageHandler = createMouseMessageHandler(documentRect, box);
		this.textFieldModel = textFieldModel;
	}

	@Override
	public MessageResponse onMessage(MessageContext messageContext, Message message) {
		if (message instanceof MouseMessage) {
			return handleMouseMessage(messageContext, message);
		} else if (message instanceof KeyMessage keyMessage) {
			return handleKeyMessage(keyMessage);
		} else if (message instanceof CharMessage charMessage) {
			return handleCharMessage(charMessage);
		}
		
		return null;
	}

	private MessageResponse handleMouseMessage(MessageContext messageContext, Message message) {
		MessageResponse mouseMessageResponse = mouseMessageHandler.onMessage(messageContext, message);
		return mouseMessageResponse;
	}
	
	private MessageResponse handleKeyMessage(KeyMessage message) {
		int action = message.getScreenEvent().getAction();
		int keyCode = message.getScreenEvent().getCode();
		
		if (action == KeyboardConstants.KEY_RELEASE) {
			return null;
		}
		
		if (keyCode == KeyboardConstants.VK_BACKSPACE) {
			textFieldModel.delete(-1);
		}
		
		return null;
	}
	
	private MessageResponse handleCharMessage(CharMessage charMessage) {
		int codepoint = charMessage.getScreenEvent().getCodepoint();
		String ch = new String(new int[] { codepoint }, 0, 1);
		textFieldModel.insert(ch);
		
		return null;
	}

	private MouseMessageHandler createMouseMessageHandler(Rectangle documentRect, Box box) {
		MessageHandler mainHandler = this;
		return new MouseMessageHandler(documentRect, Optional.ofNullable(box)) {
			@Override
			protected void onMouseEvent(MessageContext messageContext, MouseEvent mouseEvent) {
				if (mouseEvent.getButton() == MouseConstants.LEFT_BUTTON && mouseEvent.getAction() == MouseConstants.PRESS) {
					messageContext.getFocusManager().setFocused(mainHandler);
				}
			}
		};
	}
	
}
