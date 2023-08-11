package com.github.webicitybrowser.webicitybrowser.gui.ui.frame;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent;

public class FrameContext implements Context {
	
	private final ComponentUI componentUI;
	
	private ScreenContent screenContent;

	public FrameContext(ComponentUI componentUI) {
		this.componentUI = componentUI;
		FrameRendererChangeHandler.addFrameEventListener(componentUI, content -> screenContent = content);
	}
	
	public ComponentUI componentUI() {
		return this.componentUI;
	}
	
	public ScreenContent screenContent() {
		return this.screenContent;
	}
	
}
