package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.imp;

import java.util.HashMap;
import java.util.Map;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUIFactory;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeelBuilder;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class LookAndFeelBuilderImp implements LookAndFeelBuilder {

	private Map<Class<? extends Component>, ComponentUIFactory> registeredComponentUIs = new HashMap<>();

	@Override
	public <T extends Component> void registerComponentUI(Class<T> componentClass, ComponentUIFactory provider) {
		registeredComponentUIs.put(componentClass, provider);
	}

	@Override
	public LookAndFeel build() {
		return new LookAndFeelImp(Map.copyOf(registeredComponentUIs));
	}

}
