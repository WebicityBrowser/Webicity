package com.github.webicitybrowser.thready.gui.graphical.base.imp;

import com.github.webicitybrowser.thready.gui.graphical.message.keyboard.CharMessage;
import com.github.webicitybrowser.thready.gui.graphical.message.keyboard.KeyMessage;
import com.github.webicitybrowser.thready.gui.graphical.message.mouse.MouseMessage;
import com.github.webicitybrowser.thready.gui.message.Message;
import com.github.webicitybrowser.thready.windowing.core.event.ScreenEvent;
import com.github.webicitybrowser.thready.windowing.core.event.keyboard.CharScreenEvent;
import com.github.webicitybrowser.thready.windowing.core.event.keyboard.KeyScreenEvent;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.MouseScreenEvent;

public final class MessageConverter {

	private MessageConverter() {}

	public static Message convertEventToMessage(ScreenEvent e) {
		if (e instanceof MouseScreenEvent mouseEvent) {
			return createMouseMessage(mouseEvent);
		} else if (e instanceof KeyScreenEvent keyboardEvent) {
			return createKeyboardMessage(keyboardEvent);
		} else if (e instanceof CharScreenEvent charEvent) {
			return createCharMessage(charEvent);
		} else {
			return null;
		}
	}

	private static Message createMouseMessage(MouseScreenEvent e) {
		return new MouseMessage() {	
			@Override
			public boolean isExternal() {
				return e.isExternal();
			}

			@Override
			public MouseScreenEvent getScreenEvent() {
				return e;
			}
		};
	}
	
	private static Message createKeyboardMessage(KeyScreenEvent e) {
		return new KeyMessage() {
			@Override
			public KeyScreenEvent getScreenEvent() {
				return e;
			}
		};
	}
	
	private static Message createCharMessage(CharScreenEvent charEvent) {
		return new CharMessage() {
			@Override
			public CharScreenEvent getScreenEvent() {
				return charEvent;
			}
		};
	}
	
}
