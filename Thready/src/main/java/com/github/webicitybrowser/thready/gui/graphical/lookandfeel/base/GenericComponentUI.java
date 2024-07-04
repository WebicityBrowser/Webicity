package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base;

import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class GenericComponentUI implements ComponentUI {

	private final Component component;
	private final ComponentUI parent;
	private final UIDisplay<?, ?, ?> display;

	public GenericComponentUI(Component component, ComponentUI parent, UIDisplay<?, ?, ?> display) {
		this.component = component;
		this.parent = parent;
		this.display = display;
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
		return display;
	}

}
