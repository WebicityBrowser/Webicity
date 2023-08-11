package com.github.webicitybrowser.webicitybrowser.gui.ui.button;

import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.webicitybrowser.component.CircularButtonComponent;

public class CircularButtonComponentUI implements ComponentUI {

	private static final UIDisplay<?, ?, ?> CIRCULAR_BUTTON_DISPLAY = new CircularButtonDisplay();
	
	private final CircularButtonComponent component;
	private final ComponentUI parent;

	public CircularButtonComponentUI(Component component, ComponentUI parent) {
		this.component = (CircularButtonComponent) component;
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
		return CIRCULAR_BUTTON_DISPLAY;
	}

}
