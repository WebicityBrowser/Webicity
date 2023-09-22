package com.github.webicitybrowser.webicitybrowser.gui.ui.frame;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.message.mouse.MouseMessage;
import com.github.webicitybrowser.thready.gui.graphical.message.mouse.MouseMessageResponse;
import com.github.webicitybrowser.thready.gui.graphical.message.mouse.ScrollMessage;
import com.github.webicitybrowser.thready.gui.message.Message;
import com.github.webicitybrowser.thready.gui.message.MessageContext;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.message.MessageResponse;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.MouseScreenEvent;

public class FrameMessageHandler implements MessageHandler {

	private FrameUnit unit;
	private Rectangle documentRect;

	public FrameMessageHandler(FrameUnit unit, Rectangle documentRect) {
		this.unit = unit;
		this.documentRect = documentRect;
	}

	@Override
	public MessageResponse onMessage(MessageContext context, Message message) {
		if (message instanceof MouseMessage mouseMessage) {
			return handleMouseMessage(context, mouseMessage);
		} else if (message instanceof ScrollMessage scrollMessage) {
			return handleScrollMessage(context, scrollMessage);
		}

		return null;
	}

	private MouseMessageResponse handleMouseMessage(MessageContext context, MouseMessage mouseMessage) {
		context.getSloppyFocusManager().setFocused(this, context);
		MouseScreenEvent translatedScreenEvent = translateScreenEvent(mouseMessage.getScreenEvent());
		unit.screenContent().handleEvent(translatedScreenEvent, documentRect.size());

		return (MouseMessageResponse) () -> !mouseMessage.isExternal();
	}

	private MessageResponse handleScrollMessage(MessageContext context, ScrollMessage scrollMessage) {
		unit.box().displayContext().componentUI().invalidate(InvalidationLevel.PAINT);
		unit.screenContent().handleEvent(scrollMessage.getScreenEvent(), documentRect.size());

		return null;
	}

	private MouseScreenEvent translateScreenEvent(MouseScreenEvent screenEvent) {
		AbsolutePosition documentPosition = documentRect.position();
		AbsolutePosition originalViewportPosition = screenEvent.getViewportPosition();
		AbsolutePosition adjustedViewportPosition = new AbsolutePosition(
			originalViewportPosition.x() - documentPosition.x(),
			originalViewportPosition.y() - documentPosition.y());
		return new FrameMouseEvent(screenEvent, adjustedViewportPosition);
	}

}
