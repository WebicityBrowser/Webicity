package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.imp.LookAndFeelBuilderImp;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public interface LookAndFeelBuilder {

	<T extends Component> void registerComponentUI(Class<T> componentClass, ComponentUIFactory provider);
	
	LookAndFeel build();

	public static LookAndFeelBuilder create() {
		return new LookAndFeelBuilderImp();
	}
	
}
