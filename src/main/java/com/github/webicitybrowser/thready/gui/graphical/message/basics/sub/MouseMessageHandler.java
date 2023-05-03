package com.github.webicitybrowser.thready.gui.graphical.message.basics.sub;

import java.util.Optional;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.util.RectangleUtil;
import com.github.webicitybrowser.thready.gui.graphical.directive.ExternalMouseListenerDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.MouseListenerDirective;
import com.github.webicitybrowser.thready.gui.graphical.event.MouseEvent;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.message.MouseMessage;
import com.github.webicitybrowser.thready.gui.graphical.message.MouseMessageResponse;
import com.github.webicitybrowser.thready.gui.message.Message;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.message.MessageResponse;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.MouseListener;

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
	public MessageResponse onMessage(Message message) {
		if (message instanceof MouseMessage) {
			return handleMouseMessage((MouseMessage) message);
		}
		
		return null;
	}

	private MessageResponse handleMouseMessage(MouseMessage message) {
		if (message.isExternal() || !mouseCollides(message)) {
			processExternalMessage(message);
			return null;
		} else {
			return processNormalMessage(message);
		}
	}

	private MessageResponse processNormalMessage(MouseMessage message) {
		boolean isSource = !passChildrenMessage(message);
		MouseListener ownListener = getMouseListener();
		if (ownListener != null) {
			ownListener.accept(createMouseEvent(message, isSource));
			return (MouseMessageResponse) () -> true;
		}
		if (!isSource) {
			return (MouseMessageResponse) () -> true;
		}
		
		return null;
	}

	private void processExternalMessage(MouseMessage message) {
		passChildrenMessage(message);
		MouseListener ownListener = getExternalMouseListener();
		if (ownListener != null) {
			ownListener.accept(createMouseEvent(message, false));
		}
	}

	private boolean mouseCollides(MouseMessage message) {
		return RectangleUtil.containsPoint(documentRect, message.getViewportPosition());
	}
	
	private boolean passChildrenMessage(MouseMessage message) {
		boolean childResponded = false;
		
		MouseMessage currentMessage = message;
		for (MessageHandler child: children) {
			MouseMessageResponse response = (MouseMessageResponse) child.onMessage(currentMessage);
			if (!childResponded && response != null && response.getCaptured()) {
				childResponded = true;
				currentMessage = convertToExternalMessage(currentMessage);
			}
		}
		
		return childResponded;
	}

	private MouseEvent createMouseEvent(MouseMessage message, boolean isSource) {
		return new MouseEvent() {
			@Override
			public Component getComponent() {
				return box.get().getOwningComponent();
			}
			
			@Override
			public int getButton() {
				return message.getButton();
			}
			
			@Override
			public int getAction() {
				return message.getAction();
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
				return message.getViewportPosition();
			}
			
			@Override
			public AbsolutePosition getScreenPosition() {
				return message.getScreenPosition();
			}
		};
	}
	
	private MouseMessage convertToExternalMessage(MouseMessage currentMessage) {
		return new MouseMessage() {
			@Override
			public int getButton() {
				return currentMessage.getButton();
			}
			
			@Override
			public int getAction() {
				return currentMessage.getAction();
			}
			
			@Override
			public boolean isExternal() {
				return true;
			}
			
			@Override
			public AbsolutePosition getViewportPosition() {
				return currentMessage.getViewportPosition();
			}
			
			@Override
			public AbsolutePosition getScreenPosition() {
				return currentMessage.getScreenPosition();
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
