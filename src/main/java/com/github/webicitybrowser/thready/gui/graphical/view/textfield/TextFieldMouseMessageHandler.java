package com.github.webicitybrowser.thready.gui.graphical.view.textfield;

import java.util.Optional;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.gui.graphical.event.MouseEvent;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.message.basics.sub.MouseMessageHandler;
import com.github.webicitybrowser.thready.gui.graphical.viewmodel.textfield.TextFieldViewModel;
import com.github.webicitybrowser.thready.gui.message.MessageContext;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.MouseConstants;

public class TextFieldMouseMessageHandler extends MouseMessageHandler {

	private final Rectangle contentRect;
	private final TextFieldViewModel textFieldViewModel;
	private final MessageHandler mainHandler;
	private final FontMetrics fontMetrics;

	public TextFieldMouseMessageHandler(
		Rectangle documentRect, Rectangle contentRect, Optional<Box> box,
		TextFieldViewModel textFieldViewModel, MessageHandler mainHandler, Font2D font
	) {
		super(documentRect, box);
		this.contentRect = contentRect;
		this.textFieldViewModel = textFieldViewModel;
		this.mainHandler = mainHandler;
		this.fontMetrics = font.getMetrics();
	}
	
	@Override
	protected void onInternalMouseEvent(MessageContext messageContext, MouseEvent mouseEvent) {
		if (mouseEvent.getButton() == MouseConstants.LEFT_BUTTON && mouseEvent.getAction() == MouseConstants.PRESS) {
			messageContext.getFocusManager().setFocused(mainHandler, messageContext);
			int cursorPos = detectCursorPos(mouseEvent);
			textFieldViewModel.setCursorPos(cursorPos);
		}
	}

	private int detectCursorPos(MouseEvent mouseEvent) {
		AbsolutePosition mousePosition = mouseEvent.getViewportPosition();
		float offsetX = mousePosition.x() - contentRect.position().x();
		if (offsetX < 0) {
			return 0;
		}
		
		float totalWidth = 0;
		String text = textFieldViewModel.getText();
		int textLength = text.length();
		for (int i = 0; i < textLength; i++) {
			float charWidth = fontMetrics.getCharacterWidth(text.codePointAt(i));
			totalWidth += charWidth;
			if (totalWidth - 2 > offsetX) {
				return i;
			}
		}
		
		return textLength;
	}
	
}
