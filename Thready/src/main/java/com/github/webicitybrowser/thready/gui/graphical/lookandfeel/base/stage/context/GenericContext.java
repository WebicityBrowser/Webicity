package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.context;


import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;

public class GenericContext implements Context {
	
	private final UIDisplay<?, ?, ?> display;
	private final ComponentUI componentUI;

	public GenericContext(UIDisplay<?, ?, ?> display, ComponentUI componentUI) {
		this.display = display;
		this.componentUI = componentUI;
	}

	@Override
	public UIDisplay<?, ?, ?> display() {
		return this.display;
	}

	@Override
	public ComponentUI componentUI() {
		return this.componentUI;
	}
	
}
