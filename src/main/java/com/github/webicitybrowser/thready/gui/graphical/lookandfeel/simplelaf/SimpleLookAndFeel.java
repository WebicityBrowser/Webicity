package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeelBuilder;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.ContainerUI;
import com.github.webicitybrowser.thready.gui.tree.basics.ContainerComponent;

public final class SimpleLookAndFeel {

	private SimpleLookAndFeel() {}

	public static void installTo(LookAndFeelBuilder lookAndFeelBuilder) {
		lookAndFeelBuilder.registerComponentUI(ContainerComponent.class, ContainerUI::new);
	}
	
}
