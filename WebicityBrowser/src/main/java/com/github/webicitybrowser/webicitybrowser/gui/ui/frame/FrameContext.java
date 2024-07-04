package com.github.webicitybrowser.webicitybrowser.gui.ui.frame;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.context.GenericContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent;

public class FrameContext extends GenericContext {
	
	private ScreenContent screenContent;

	public FrameContext(UIDisplay<?, ?, ?> display, ComponentUI componentUI) {
		super(display, componentUI);
		FrameRendererChangeHandler.addFrameEventListener(componentUI, content -> screenContent = content);
	}
	
	public ScreenContent screenContent() {
		return this.screenContent;
	}
	
}
