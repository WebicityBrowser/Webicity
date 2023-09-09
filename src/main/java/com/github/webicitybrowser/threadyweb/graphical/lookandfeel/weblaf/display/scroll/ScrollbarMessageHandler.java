package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.scroll;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.util.RectangleUtil;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.message.mouse.MouseMessage;
import com.github.webicitybrowser.thready.gui.graphical.message.mouse.MouseMessageResponse;
import com.github.webicitybrowser.thready.gui.message.Message;
import com.github.webicitybrowser.thready.gui.message.MessageContext;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.message.MessageResponse;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.MouseConstants;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.MouseScreenEvent;

public class ScrollbarMessageHandler implements MessageHandler {

	private final ScrollUnit unit;
	private final Rectangle documentRect;
	private final MessageHandler innerMessageHandler;

	public ScrollbarMessageHandler(ScrollUnit unit, Rectangle documentRect, MessageHandler innerMessageHandler) {
		this.unit = unit;
		this.documentRect = documentRect;
		this.innerMessageHandler = innerMessageHandler;
	}

	@Override
	public MessageResponse onMessage(MessageContext context, Message message) {
		if (message instanceof MouseMessage mouseMessage) {
			return handleMouseMessage(context, mouseMessage);
		} else {
			return innerMessageHandler.onMessage(context, message);
		}
	}

	private MessageResponse handleMouseMessage(MessageContext context, MouseMessage mouseMessage) {
		Rectangle childDocumentRect = new Rectangle(documentRect.position(), unit.innerUnitSize());
		boolean isInChild = RectangleUtil.containsPoint(childDocumentRect, mouseMessage.getScreenEvent().getViewportPosition());
		boolean isScrolling = unit.verticalScrollState().isScrolling() || unit.horizontalScrollState().isScrolling();
		if (isInChild && !isScrolling) {
			return innerMessageHandler.onMessage(context, mouseMessage);
		} else {
			sendExternalMessage(context, mouseMessage);
			return handleScrollbarMessage(context, mouseMessage);
		}
	}

	private MessageResponse handleScrollbarMessage(MessageContext context, MouseMessage mouseMessage) {
		MouseScreenEvent screenEvent = mouseMessage.getScreenEvent();
		if (screenEvent.getButton() != MouseConstants.LEFT_BUTTON) return null;

		unit.box().scrollContext().componentUI().invalidate(InvalidationLevel.PAINT);
		if (unit.verticalScrollState().isScrolling()) {
			handleScrollbarMove(screenEvent, ScrollbarStyles.VERTICAL_SCROLLBAR, unit.verticalScrollState());
			return (MouseMessageResponse) () -> true;
		} else if (unit.horizontalScrollState().isScrolling()) {
			handleScrollbarMove(screenEvent, ScrollbarStyles.HORIZONTAL_SCROLLBAR, unit.horizontalScrollState());
			return (MouseMessageResponse) () -> true;
		} else {
			handleScrollbarClick(context, screenEvent);
			return (MouseMessageResponse) () -> !screenEvent.isExternal();
		}
	}

	private void handleScrollbarMove(MouseScreenEvent screenEvent, ScrollbarDimensionTranslator positionTranslator, ScrollState scrollState) {
		AbsolutePosition translatedMousePosition = positionTranslator.translateToUpright(screenEvent.getViewportPosition());
		float scrollBarMovement = translatedMousePosition.y() - scrollState.scrollStartPosition();
		float pageScrollChange = ScrollUtils.computePageScrollChange(unit, documentRect, scrollBarMovement, positionTranslator);
		AbsolutePosition translatedOriginalScrollPosition = positionTranslator.translateToUpright(
			unit.box().scrollContext().scrollPosition());
		float pageOverflow = unit.innerUnit().fitSize().height() - unit.innerUnitSize().height();
		float scrollComponent = scrollState.initialPageScrollPosition() + pageScrollChange;
		scrollComponent = Math.max(0, Math.min(scrollComponent, pageOverflow));
		AbsolutePosition translatedNewScrollPosition = new AbsolutePosition(
			translatedOriginalScrollPosition.x(),
			scrollComponent);
		unit.box().scrollContext().setScrollPosition(
			positionTranslator.translateFromUpright(translatedNewScrollPosition));
		if (screenEvent.getAction() == MouseConstants.RELEASE) {
			scrollState.endScroll();
		}
	}

	private void handleScrollbarClick(MessageContext context, MouseScreenEvent screenEvent) {
		boolean isInDocumentRect = RectangleUtil.containsPoint(documentRect, screenEvent.getViewportPosition());
		if (screenEvent.getAction() != MouseConstants.PRESS || screenEvent.isExternal() || !isInDocumentRect) return;
		if (clickedScrollbar(screenEvent, ScrollbarStyles.VERTICAL_SCROLLBAR)) {
			startScrollbarDragged(screenEvent, ScrollbarStyles.VERTICAL_SCROLLBAR, unit.verticalScrollState());
		} else if (clickedScrollbar(screenEvent, ScrollbarStyles.HORIZONTAL_SCROLLBAR)) {
			startScrollbarDragged(screenEvent, ScrollbarStyles.HORIZONTAL_SCROLLBAR, unit.horizontalScrollState());
		}
	}

	private boolean clickedScrollbar(MouseScreenEvent screenEvent, ScrollbarDimensionTranslator positionTranslator) {
		Rectangle scrollbarLocation = ScrollUtils.getScrollbarLocation(unit, documentRect, positionTranslator);
		AbsolutePosition mousePosition = positionTranslator.translateToUpright(screenEvent.getViewportPosition());

		return RectangleUtil.containsPoint(scrollbarLocation, mousePosition);
	}

	private void startScrollbarDragged(MouseScreenEvent screenEvent, ScrollbarDimensionTranslator positionTranslator, ScrollState scrollState) {
		AbsolutePosition translatedMousePosition = positionTranslator.translateToUpright(screenEvent.getViewportPosition());
		scrollState.startScroll(translatedMousePosition.y());
	}

	private void sendExternalMessage(MessageContext context, MouseMessage mouseMessage) {
		MouseMessage externalMessage = MouseMessage.create(mouseMessage.getScreenEvent(), true);
		innerMessageHandler.onMessage(context, externalMessage);
	}

}
