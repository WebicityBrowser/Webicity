package com.github.webicitybrowser.webicitybrowser.gui.ui.frame;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent;

public class FrameContext implements Context {
	
	private final UIDisplay<?, ?, ?> display;
	private final ComponentUI componentUI;
	
	private ScreenContent screenContent;

	public FrameContext(UIDisplay<?, ?, ?> display, ComponentUI componentUI) {
		this.display = display;
		this.componentUI = componentUI;
		FrameRendererChangeHandler.addFrameEventListener(componentUI, content -> screenContent = content);
	}

	@Override
	public UIDisplay<?, ?, ?> display() {
		return this.display;
	}

	@Override
	public ComponentUI componentUI() {
		return this.componentUI;
	}
	
	public ScreenContent screenContent() {
		return this.screenContent;
	}
	
}
