package com.github.webicitybrowser.thready.gui.graphical.view.textfield;

import com.github.webicitybrowser.thready.gui.graphical.message.keyboard.CharMessage;
import com.github.webicitybrowser.thready.gui.graphical.message.keyboard.KeyMessage;
import com.github.webicitybrowser.thready.gui.graphical.viewmodel.textfield.TextFieldViewModel;
import com.github.webicitybrowser.thready.gui.message.Message;
import com.github.webicitybrowser.thready.gui.message.MessageContext;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.message.MessageResponse;
import com.github.webicitybrowser.thready.windowing.core.event.keyboard.KeyboardConstants;

public class TextFieldKeyboardMessageHandler implements MessageHandler {

	private final TextFieldViewModel textFieldViewModel;

	public TextFieldKeyboardMessageHandler(TextFieldViewModel textFieldViewModel) {
		this.textFieldViewModel = textFieldViewModel;
	}

	@Override
	public MessageResponse onMessage(MessageContext context, Message message) {
		if (message instanceof KeyMessage keyMessage) {
			return handleKeyMessage(keyMessage);
		} else if (message instanceof CharMessage charMessage) {
			return handleCharMessage(charMessage);
		} else {
			return null;
		}
	}
	
	private MessageResponse handleKeyMessage(KeyMessage message) {
		int action = message.getScreenEvent().getAction();
		int keyCode = message.getScreenEvent().getCode();
		
		if (action == KeyboardConstants.KEY_RELEASE) {
			return null;
		}
		
		switch (keyCode) {
		case KeyboardConstants.VK_BACKSPACE:
			textFieldViewModel.delete(-1);
			break;
		case KeyboardConstants.VK_LEFT:
			textFieldViewModel.moveCursor(-1);
			break;
		case KeyboardConstants.VK_RIGHT:
			textFieldViewModel.moveCursor(1);
			break;
		case KeyboardConstants.VK_INSERT:
			textFieldViewModel.toggleReplaceMode();
			break;
		case KeyboardConstants.VK_HOME:
			textFieldViewModel.setCursorPos(0);
			break;
		case KeyboardConstants.VK_END:
			textFieldViewModel.setCursorToEnd();
		case KeyboardConstants.VK_DELETE:
			textFieldViewModel.delete(1);
			break;
		case KeyboardConstants.VK_ENTER:
			textFieldViewModel.submit();
			break;
		default:
		}
		
		return null;
	}
	
	private MessageResponse handleCharMessage(CharMessage charMessage) {
		int codepoint = charMessage.getScreenEvent().getCodepoint();
		String ch = new String(new int[] { codepoint }, 0, 1);
		textFieldViewModel.insert(ch);
		
		return null;
	}

}
