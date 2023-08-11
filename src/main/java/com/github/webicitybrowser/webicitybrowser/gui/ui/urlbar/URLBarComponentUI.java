package com.github.webicitybrowser.webicitybrowser.gui.ui.urlbar;

import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.webicitybrowser.component.URLBarComponent;

public class URLBarComponentUI implements ComponentUI {
	
	private static final UIDisplay<?, ?, ?> UI_DISPLAY = new URLBarDisplay();

	private final URLBarComponent component;
	private final ComponentUI parent;

	public URLBarComponentUI(Component component, ComponentUI parent) {
		this.component = (URLBarComponent) component;
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
		return UI_DISPLAY;
	}

}
