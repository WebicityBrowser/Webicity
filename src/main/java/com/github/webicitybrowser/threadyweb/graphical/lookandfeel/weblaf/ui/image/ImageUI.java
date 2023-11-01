package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image;

import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class ImageUI implements ComponentUI {

	private static final UIDisplay<?, ?, ?> IMAGE_DISPLAY = new ImageDisplay();

	private final Component component;
	private final ComponentUI parent;
	
	public ImageUI(Component component, ComponentUI parent) {
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
		return IMAGE_DISPLAY;
	}
	


}
