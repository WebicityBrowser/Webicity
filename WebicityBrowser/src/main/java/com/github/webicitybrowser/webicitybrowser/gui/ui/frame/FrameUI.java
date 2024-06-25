package com.github.webicitybrowser.webicitybrowser.gui.ui.frame;

import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class FrameUI implements ComponentUI {
	
	private static final UIDisplay<?, ?, ?> FRAME_DISPLAY = new FrameDisplay();
	
	private final Component component;
	private final ComponentUI parent;

	public FrameUI(Component component, ComponentUI parent) {
		this.component = component;
		this.parent = parent;
	}

	@Override
	public Component getComponent() {
		return this.component;
	}
	
	@Override
	public void invalidate(InvalidationLevel level) {
		parent.invalidate(level);
	}

	@Override
	public UIDisplay<?, ?, ?> getRootDisplay() {
		return FRAME_DISPLAY;
	}

}
