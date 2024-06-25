package com.github.webicitybrowser.thready.gui.graphical.base.imp;

import com.github.webicitybrowser.thready.gui.graphical.animation.AnimationContext;

public class AnimationContextImp implements AnimationContext {

	private long deltaMillis;
	private long lastMillis;

	public AnimationContextImp() {
		lastMillis = System.currentTimeMillis();
	}

	@Override
	public long getDeltaMillis() {
		return deltaMillis;
	}

	public void tick() {
		long currentMillis = System.currentTimeMillis();
		deltaMillis = currentMillis - lastMillis;
		lastMillis = currentMillis;
	}
	
}
