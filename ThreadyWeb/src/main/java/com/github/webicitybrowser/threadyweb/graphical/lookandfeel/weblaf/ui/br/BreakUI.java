package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.br;

import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

/**
 * This class is used to designate the display of a break element
 * for the WebLAF look and feel.
 */
public class BreakUI implements ComponentUI {

	private static final UIDisplay<?, ?, ?> BREAK_DISPLAY = new BreakDisplay();

	private final Component component;
	private final ComponentUI parent;

	public BreakUI(Component component, ComponentUI parent) {
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
		return BREAK_DISPLAY;
	}
	
}
