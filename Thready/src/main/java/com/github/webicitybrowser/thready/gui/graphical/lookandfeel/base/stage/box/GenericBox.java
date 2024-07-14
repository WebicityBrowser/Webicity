package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public record GenericBox<T extends Component, U extends Context>(U displayContext) implements Box {
	
	@SuppressWarnings("unchecked")
	@Override
	public T owningComponent() {
		return (T) displayContext.componentUI().getComponent();
	}

	@Override
	public UIDisplay<?, ?, ?> display() {
		return displayContext.display();
	}
	
}
