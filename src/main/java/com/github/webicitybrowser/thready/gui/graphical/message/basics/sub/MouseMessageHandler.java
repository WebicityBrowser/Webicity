package com.github.webicitybrowser.thready.gui.graphical.message.basics.sub;

import java.util.Optional;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.util.RectangleUtil;
import com.github.webicitybrowser.thready.gui.graphical.directive.ExternalMouseListenerDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.MouseListenerDirective;
import com.github.webicitybrowser.thready.gui.graphical.event.MouseEvent;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.message.mouse.MouseMessage;
import com.github.webicitybrowser.thready.gui.graphical.message.mouse.MouseMessageResponse;
import com.github.webicitybrowser.thready.gui.message.Message;
import com.github.webicitybrowser.thready.gui.message.MessageContext;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.message.MessageResponse;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.MouseListener;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.MouseScreenEvent;

public class MouseMessageHandler implements MessageHandler {

	private final Rectangle documentRect;
	private final Optional<Box> box;
	private final MessageHandler[] children;

	public MouseMessageHandler(Rectangle documentRect, Optional<Box> box, MessageHandler... children) {
		if (children.length > 0 && children[0] == null) {
			throw new RuntimeException();
		}
		
		this.documentRect = documentRect;
		this.box = box;
		this.children = children;
	}
	
	@Override
	public MessageResponse onMessage(MessageContext messageContext, Message message) {
		if (message instanceof MouseMessage) {
			return handleMouseMessage(messageContext, (MouseMessage) message);
		}
		
		return null;
	}
	
	protected void onMouseEvent(MessageContext messageContext, MouseEvent mouseEvent) {
		// Override this
	}

	private MessageResponse handleMouseMessage(MessageContext messageContext, MouseMessage message) {
		if (message.isExternal() || !mouseCollides(message)) {
			processExternalMessage(messageContext, message);
			return null;
		} else {
			return processNormalMessage(messageContext, message);
		}
	}

	private MessageResponse processNormalMessage(MessageContext messageContext, MouseMessage message) {
		boolean isSource = !passChildrenMessage(messageContext, message);
		MouseEvent mouseEvent = createMouseEvent(message, isSource);
		onMouseEvent(messageContext, mouseEvent);
		MouseListener ownListener = getMouseListener();
		if (ownListener != null) {
			ownListener.accept(mouseEvent);
			return (MouseMessageResponse) () -> true;
		}
		if (!isSource) {
			return (MouseMessageResponse) () -> true;
		}
		
		return null;
	}

	private void processExternalMessage(MessageContext messageContext, MouseMessage message) {
		passChildrenMessage(messageContext, message);
		MouseListener ownListener = getExternalMouseListener();
		if (ownListener != null) {
			ownListener.accept(createMouseEvent(message, false));
		}
	}

	private boolean mouseCollides(MouseMessage message) {
		AbsolutePosition mousePosition = message.getScreenEvent().getViewportPosition();
		return RectangleUtil.containsPoint(documentRect, mousePosition);
	}
	
	private boolean passChildrenMessage(MessageContext messageContext, MouseMessage message) {
		boolean childResponded = false;
		
		MouseMessage currentMessage = message;
		for (MessageHandler child: children) {
			MouseMessageResponse response = (MouseMessageResponse) child.onMessage(messageContext, currentMessage);
			if (!childResponded && response != null && response.getCaptured()) {
				childResponded = true;
				currentMessage = convertToExternalMessage(currentMessage);
			}
		}
		
		return childResponded;
	}

	private MouseEvent createMouseEvent(MouseMessage message, boolean isSource) {
		MouseScreenEvent event = message.getScreenEvent();
		
		return new MouseEvent() {
			@Override
			public Component getComponent() {
				return box.get().getOwningComponent();
			}
			
			@Override
			public int getButton() {
				return event.getButton();
			}
			
			@Override
			public int getAction() {
				return event.getAction();
			}
			
			@Override
			public boolean isSource() {
				return isSource;
			}
			
			@Override
			public boolean isExternal() {
				return message.isExternal();
			}
			
			@Override
			public AbsolutePosition getViewportPosition() {
				return event.getViewportPosition();
			}
			
			@Override
			public AbsolutePosition getScreenPosition() {
				return event.getScreenPosition();
			}
		};
	}
	
	private MouseMessage convertToExternalMessage(MouseMessage message) {
		return new MouseMessage() {
			@Override
			public boolean isExternal() {
				return true;
			}

			@Override
			public MouseScreenEvent getScreenEvent() {
				return message.getScreenEvent();
			}
		};
	}

	private MouseListener getMouseListener() {
		return box
			.map(b -> b.getStyleDirectives())
			.flatMap(directives -> directives.getDirectiveOrEmpty(MouseListenerDirective.class))
			.map(directive -> directive.getMouseListener())
			.orElse(null);
	}
	
	private MouseListener getExternalMouseListener() {
		return box
			.map(b -> b.getStyleDirectives())
			.flatMap(directives -> directives.getDirectiveOrEmpty(ExternalMouseListenerDirective.class))
			.map(directive -> directive.getMouseListener())
			.orElse(null);
	}
	
}
