package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.scroll;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.dimensions.util.RectangleUtil;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.message.mouse.MouseMessage;
import com.github.webicitybrowser.thready.gui.graphical.message.mouse.MouseMessageResponse;
import com.github.webicitybrowser.thready.gui.graphical.message.mouse.ScrollMessage;
import com.github.webicitybrowser.thready.gui.message.Message;
import com.github.webicitybrowser.thready.gui.message.MessageContext;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.message.MessageResponse;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.MouseConstants;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.MouseScreenEvent;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.ScrollScreenEvent;

public class ScrollbarMessageHandler implements MessageHandler {

	private static final float MOUSE_SCROLL_MULTIPLIER = -5000;
	
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
		} else if (message instanceof ScrollMessage scrollMessage) {
			return handleScrollMessage(context, scrollMessage);
		} else {
			return innerMessageHandler.onMessage(context, message);
		}
	}

	private MessageResponse handleScrollMessage(MessageContext context, ScrollMessage scrollMessage) {
		unit.box().scrollContext().componentUI().invalidate(InvalidationLevel.PAINT);
		ScrollScreenEvent scrollEvent = scrollMessage.getScreenEvent();
		AbsolutePosition scrollPosition = unit.box().scrollContext().scrollPosition();
		AbsoluteSize pageSize = unit.innerUnit().fitSize();
		float maxScrollX = pageSize.width() - documentRect.size().width();
		float maxScrollY = pageSize.height() - documentRect.size().height();
		float deltaMillis = context.getAnimationContext().getDeltaMillis();
		if (deltaMillis > 80) deltaMillis = 0;
		float adjustedScrollMultiplier = MOUSE_SCROLL_MULTIPLIER * deltaMillis / 1000f;
		float unclampedNewScrollX = scrollPosition.x() + tweenSpeed(scrollEvent.getScrollX()) * adjustedScrollMultiplier;
		float unclampedNewScrollY = scrollPosition.y() + tweenSpeed(scrollEvent.getScrollY()) * adjustedScrollMultiplier;
		float newScrollX = scrollPosition.x() == RelativeDimension.UNBOUNDED ?
			RelativeDimension.UNBOUNDED :
			Math.max(0, Math.min(unclampedNewScrollX, maxScrollX));
		float newScrollY = scrollPosition.y() == RelativeDimension.UNBOUNDED ?
			RelativeDimension.UNBOUNDED :
			Math.max(0, Math.min(unclampedNewScrollY, maxScrollY));
		AbsolutePosition newScrollPosition = new AbsolutePosition(newScrollX, newScrollY);

		unit.box().scrollContext().setScrollPosition(newScrollPosition);

		return null;
	}

	private float tweenSpeed(float scroll) {
		if (scroll < 10 && scroll > -10) return scroll;
		return (float) Math.floor(scroll / 10f) * 10;
	}

	private MessageResponse handleMouseMessage(MessageContext context, MouseMessage mouseMessage) {
		context.getSloppyFocusManager().setFocused(this, context);
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
		AbsoluteSize translatedPageSize = positionTranslator.translateToUpright(unit.innerUnit().fitSize());
		AbsoluteSize translatedDocumentSize = positionTranslator.translateToUpright(documentRect.size());
		float pageOverflow = translatedPageSize.height() - translatedDocumentSize.height();
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
