package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element;

import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.wrapper.WebWrapperDisplay;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.contents.ContentsDisplay;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;

public class ElementUI implements ComponentUI {
	
	private static final UIDisplay<?, ?, ?> ELEMENT_DISPLAY = new ContentsDisplay(new WebWrapperDisplay<>(new ElementDisplay()));
	
	private final ElementComponent component;
	private final ComponentUI parent;

	public ElementUI(Component component, ComponentUI parent) {
		this.component = (ElementComponent) component;
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
		return ELEMENT_DISPLAY;
	}

}
