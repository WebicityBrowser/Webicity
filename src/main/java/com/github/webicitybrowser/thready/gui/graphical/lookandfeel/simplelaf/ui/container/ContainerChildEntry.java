package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public record ContainerChildEntry(ComponentUI componentUI, Context context) {

	public Component component() {
		return componentUI.getComponent();
	}
	
}
