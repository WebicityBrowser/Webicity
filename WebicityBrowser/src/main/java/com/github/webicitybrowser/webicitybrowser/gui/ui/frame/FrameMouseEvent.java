package com.github.webicitybrowser.webicitybrowser.gui.ui.frame;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.MouseScreenEvent;

public class FrameMouseEvent implements MouseScreenEvent {

	private final MouseScreenEvent innerScreenEvent;
	private final AbsolutePosition viewportPosition;

	public FrameMouseEvent(MouseScreenEvent innerScreenEvent, AbsolutePosition viewportPosition) {
		this.innerScreenEvent = innerScreenEvent;
		this.viewportPosition = viewportPosition;
	}

	@Override
	public int getAction() {
		return innerScreenEvent.getAction();
	}

	@Override
	public int getButton() {
		return innerScreenEvent.getButton();
	}

	@Override
	public boolean isExternal() {
		return innerScreenEvent.isExternal();
	}

	@Override
	public AbsolutePosition getViewportPosition() {
		return viewportPosition;
	}

	@Override
	public AbsolutePosition getScreenPosition() {
		return innerScreenEvent.getScreenPosition();
	}

}
