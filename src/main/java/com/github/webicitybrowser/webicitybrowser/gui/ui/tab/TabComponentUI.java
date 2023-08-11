package com.github.webicitybrowser.webicitybrowser.gui.ui.tab;

import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.webicitybrowser.gui.binding.component.tab.TabComponent;

public class TabComponentUI implements ComponentUI {

	private static final UIDisplay<?, ?, ?> TAB_DISPLAY = new TabDisplay();
	
	private final TabComponent component;
	private final ComponentUI parent;
	
	public TabComponentUI(Component component, ComponentUI parent) {
		this.component = (TabComponent) component;
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
		return TAB_DISPLAY;
	}

}
