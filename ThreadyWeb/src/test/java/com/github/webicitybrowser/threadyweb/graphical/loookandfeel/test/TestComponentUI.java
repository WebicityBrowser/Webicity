package com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test;

import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class TestComponentUI implements ComponentUI {

	private InvalidationLevel invalidationLevel = InvalidationLevel.BOX;

	@Override
	public Component getComponent() {
		throw new UnsupportedOperationException("Unimplemented method 'getComponent'");
	}

	@Override
	public void invalidate(InvalidationLevel level) {
		this.invalidationLevel = level;
	}

	@Override
	public InvalidationLevel invalidationLevel() {
		return invalidationLevel;
	}

	@Override
	public void validateUpTo(InvalidationLevel validationLevel) {
		if (invalidationLevel.compareTo(invalidationLevel) < 0) {
			invalidationLevel = validationLevel;
		}
	}

	@Override
	public UIDisplay<?, ?, ?> getRootDisplay() {
		throw new UnsupportedOperationException("Unimplemented method 'getRootDisplay'");
	}
	
}
