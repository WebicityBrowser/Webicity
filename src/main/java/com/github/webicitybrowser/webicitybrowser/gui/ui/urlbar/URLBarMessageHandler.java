package com.github.webicitybrowser.webicitybrowser.gui.ui.urlbar;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.view.textfield.TextFieldMessageHandler;
import com.github.webicitybrowser.thready.gui.graphical.viewmodel.textfield.TextFieldViewModel;
import com.github.webicitybrowser.thready.gui.message.Message;
import com.github.webicitybrowser.thready.gui.message.MessageContext;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.message.MessageResponse;
import com.github.webicitybrowser.webicitybrowser.gui.Styling;

public class URLBarMessageHandler implements MessageHandler {

	private final TextFieldMessageHandler textFieldMessageHandler;

	public URLBarMessageHandler(Rectangle documentRect, Box box, TextFieldViewModel textFieldViewModel, Font2D font) {
		this.textFieldMessageHandler = new TextFieldMessageHandler(getContentRect(documentRect), box, textFieldViewModel);
	}

	@Override
	public MessageResponse onMessage(MessageContext messageContext, Message message) {
		return textFieldMessageHandler.onMessage(messageContext, message);
	}
	
	private Rectangle getContentRect(Rectangle documentRect) {
		float xOffset = Styling.BUTTON_WIDTH / 2;
		
		AbsolutePosition originalPosition = documentRect.position();
		AbsolutePosition adjustedPosition = new AbsolutePosition(originalPosition.x() + xOffset, originalPosition.y());
		
		AbsoluteSize originalSize = documentRect.size();
		AbsoluteSize adjustedSize = new AbsoluteSize(
			Math.max(0, originalSize.width() - xOffset * 2),
			originalSize.height());
		
		return new Rectangle(adjustedPosition, adjustedSize);
	}
	
}
