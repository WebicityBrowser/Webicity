package com.github.webicitybrowser.thready.gui.message;

import com.github.webicitybrowser.thready.gui.graphical.animation.AnimationContext;

public interface MessageContext {

	FocusManager getFocusManager();

	FocusManager getSloppyFocusManager();

	AnimationContext getAnimationContext();
	
}
