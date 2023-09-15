package com.github.webicitybrowser.webicitybrowser.gui.ui.tab;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.message.basics.DefaultGraphicalMessageHandler;
import com.github.webicitybrowser.thready.gui.graphical.message.mouse.MouseMessage;
import com.github.webicitybrowser.thready.gui.graphical.message.mouse.MouseMessageResponse;
import com.github.webicitybrowser.thready.gui.message.Message;
import com.github.webicitybrowser.thready.gui.message.MessageContext;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.message.MessageResponse;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.MouseConstants;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.MouseScreenEvent;
import com.github.webicitybrowser.webicitybrowser.gui.Styling;

public class TabMessageHandler implements MessageHandler {
	
	private final Rectangle documentRect;
	private final TabUnit unit;
	private final DefaultGraphicalMessageHandler defaultGraphicalMessageHandler;

	public TabMessageHandler(Rectangle documentRect, TabUnit unit) {
		this.documentRect = documentRect;
		this.unit = unit;
		this.defaultGraphicalMessageHandler = new DefaultGraphicalMessageHandler(documentRect, unit.box());
	}

	@Override
	public MessageResponse onMessage(MessageContext context, Message message) {
		if (message instanceof MouseMessage mouseMessage && handleMouseMessage(mouseMessage)) {
			return (MouseMessageResponse) () -> true;
		}
		return defaultGraphicalMessageHandler.onMessage(context, message);
	}

	private boolean handleMouseMessage(MouseMessage mouseMessage) {
		if (mouseMessage.isExternal()) return false;
		MouseScreenEvent event = mouseMessage.getScreenEvent();
		if (
			event.getButton() != MouseConstants.LEFT_BUTTON &&
			event.getAction() != MouseConstants.MOVE
		) return false;

		boolean mouseInXButtonBounds = mouseInXButtonBounds(event);
		boolean oldHovered = unit.buttonState().isXHovered();
		unit.buttonState().setXHovered(mouseInXButtonBounds);
		if (oldHovered != mouseInXButtonBounds) {
			unit.box().context().componentUI().invalidate(InvalidationLevel.PAINT);
		}
		if (!mouseInXButtonBounds) return false;

		return true;
	}

	private boolean mouseInXButtonBounds(MouseScreenEvent event) {
		float docX = documentRect.position().x();
		float docY = documentRect.position().y();
		float docH = documentRect.size().height();

		float halfButtonWidth = Styling.BUTTON_WIDTH / 2;
		float circleCenterX = docX + Styling.ELEMENT_PADDING + halfButtonWidth / 2;
		float circleCenterY = docY + docH / 2;
		float circleRadius = halfButtonWidth / 2;

		float mouseX = event.getViewportPosition().x();
		float mouseY = event.getViewportPosition().y();

		float distanceX = mouseX - circleCenterX;
		float distanceY = mouseY - circleCenterY;

		return distanceX * distanceX + distanceY * distanceY < circleRadius * circleRadius;
		
	}

}
