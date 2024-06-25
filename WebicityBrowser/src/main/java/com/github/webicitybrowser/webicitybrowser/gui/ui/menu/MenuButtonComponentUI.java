package com.github.webicitybrowser.webicitybrowser.gui.ui.menu;

import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.webicitybrowser.component.MenuButtonComponent;

public class MenuButtonComponentUI implements ComponentUI {

	private static final UIDisplay<?, ?, ?> MENU_BUTTON_DISPLAY = new MenuButtonDisplay();
	
	private final MenuButtonComponent component;
	private final ComponentUI parent;

	public MenuButtonComponentUI(Component component, ComponentUI parent) {
		this.component = (MenuButtonComponent) component;
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
		return MENU_BUTTON_DISPLAY;
	}

}
