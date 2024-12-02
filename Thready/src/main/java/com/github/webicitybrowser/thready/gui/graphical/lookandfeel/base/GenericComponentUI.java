package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base;

import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class GenericComponentUI implements ComponentUI {

	private final Component component;
	private final ComponentUI parent;
	private final UIDisplay<?, ?, ?> display;

	private InvalidationLevel invalidationLevel = InvalidationLevel.STYLE;

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
		this.invalidationLevel = level;
	}

	@Override
	public void validateUpTo(InvalidationLevel validationLevel) {
		if (validationLevel.compareTo(invalidationLevel) < 0) {
			invalidationLevel = validationLevel;
		}
	}
	
	@Override
	public InvalidationLevel invalidationLevel() {
		return invalidationLevel;
	}

	@Override
	public UIDisplay<?, ?, ?> getRootDisplay() {
		return display;
	}

}
