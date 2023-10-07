package com.github.webicitybrowser.thready.gui.graphical.base.imp.message;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.animation.AnimationContext;
import com.github.webicitybrowser.thready.gui.graphical.base.imp.AnimationContextImp;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.graphical.message.keyboard.CharMessage;
import com.github.webicitybrowser.thready.gui.graphical.message.keyboard.KeyMessage;
import com.github.webicitybrowser.thready.gui.graphical.message.mouse.MouseMessage;
import com.github.webicitybrowser.thready.gui.graphical.message.mouse.ScrollMessage;
import com.github.webicitybrowser.thready.gui.message.FocusManager;
import com.github.webicitybrowser.thready.gui.message.Message;
import com.github.webicitybrowser.thready.gui.message.MessageContext;
import com.github.webicitybrowser.thready.windowing.core.event.ScreenEvent;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.MouseConstants;

public class ContentMessageHandler {

	private final FocusManager focusManager = new FocusManagerImp();
	private final FocusManager sloppyFocusManager = new FocusManagerImp();

	private final AnimationContextImp animationContext = new AnimationContextImp();

	public void handleEvent(ScreenEvent e, RenderedUnit rootUnit, Rectangle documentRect) {
		if (rootUnit == null) return;

		Message message = MessageConverter.convertEventToMessage(e);
		if (message == null) {
			return;
		}
		
		MessageContext messageContext = createMessageContext();
		if (message instanceof KeyMessage || message instanceof CharMessage) {
			focusManager.messageFocused(messageContext, message);
		} else if (message instanceof ScrollMessage) {
			sloppyFocusManager.messageFocused(messageContext, message);
		} else if (message instanceof MouseMessage) {
			sloppyFocusManager.clearFocus();
			messageRoot(messageContext, message, rootUnit, documentRect);
		} else {
			messageRoot(messageContext, message, rootUnit, documentRect);
		}
	}

	private <V extends RenderedUnit> void messageRoot(MessageContext messageContext, Message message, V rootUnit, Rectangle documentRect) {
		resetFocusIfClick(messageContext, message);
		UIPipeline
			.createMessageHandler(rootUnit, documentRect)
			.onMessage(messageContext, message);
	}

	private void resetFocusIfClick(MessageContext messageContext, Message message) {
		if (
			message instanceof MouseMessage mouseMessage &&
			mouseMessage.getScreenEvent().getAction() == MouseConstants.PRESS
		) {
			focusManager.setFocused(null, messageContext);
		}
	}

	private MessageContext createMessageContext() {
		animationContext.tick();
		return new MessageContext() {
			@Override
			public FocusManager getFocusManager() {
				return focusManager;
			}

			@Override
			public FocusManager getSloppyFocusManager() {
				return sloppyFocusManager;
			}

			@Override
			public AnimationContext getAnimationContext() {
				return animationContext;
			}
		};
	}
	
}
