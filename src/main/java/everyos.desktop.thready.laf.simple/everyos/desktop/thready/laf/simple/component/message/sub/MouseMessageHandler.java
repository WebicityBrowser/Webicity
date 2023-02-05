package everyos.desktop.thready.laf.simple.component.message.sub;

import everyos.desktop.thready.basic.directive.ExternalMouseListenerDirective;
import everyos.desktop.thready.basic.directive.MouseListenerDirective;
import everyos.desktop.thready.basic.event.MouseEvent;
import everyos.desktop.thready.basic.event.MouseListener;
import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.message.Message;
import everyos.desktop.thready.core.gui.message.MessageHandler;
import everyos.desktop.thready.core.gui.message.MessageResponse;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.message.MouseMessage;
import everyos.desktop.thready.core.gui.stage.message.MouseMessageResponse;
import everyos.desktop.thready.core.positioning.AbsolutePosition;
import everyos.desktop.thready.core.positioning.Rectangle;
import everyos.desktop.thready.core.positioning.util.RectangleUtil;

public class MouseMessageHandler implements MessageHandler {

	private final Rectangle documentRect;
	private final Box box;
	private final MessageHandler[] children;

	public MouseMessageHandler(Rectangle documentRect, Box box, MessageHandler... children) {
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
				return box.getOwningComponent();
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
		if (box == null) {
			return null;
		}
		return box
			.getDirectivePool()
			.getDirectiveOrEmpty(MouseListenerDirective.class)
			.map(directive -> directive.getMouseListener())
			.orElse(null);
	}
	
	private MouseListener getExternalMouseListener() {
		if (box == null) {
			return null;
		}
		return box
			.getDirectivePool()
			.getDirectiveOrEmpty(ExternalMouseListenerDirective.class)
			.map(directive -> directive.getMouseListener())
			.orElse(null);
	}
	
}
