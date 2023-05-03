package com.github.webicitybrowser.thready.gui.graphical.base.imp;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.gui.graphical.message.MouseMessage;
import com.github.webicitybrowser.thready.gui.message.Message;
import com.github.webicitybrowser.thready.windowing.core.event.ScreenEvent;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.MouseScreenEvent;

public final class MessageConverter {

	private MessageConverter() {}

	public static Message convertEventToMessage(ScreenEvent e) {
		if (e instanceof MouseScreenEvent mouseEvent) {
			return createMouseMessage(mouseEvent);
		} else {
			return null;
		}
	}

	private static Message createMouseMessage(MouseScreenEvent e) {
		return new MouseMessage() {	
			@Override
			public boolean isExternal() {
				return false;
			}
			
			@Override
			public AbsolutePosition getViewportPosition() {
				return e.getViewportPosition();
			}
			
			@Override
			public AbsolutePosition getScreenPosition() {
				return e.getScreenPosition();
			}
			
			@Override
			public int getButton() {
				return e.getButton();
			}
			
			@Override
			public int getAction() {
				return e.getAction();
			}
		};
	}
	
}
