package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.imp;

import java.util.Map;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUIFactory;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class LookAndFeelImp implements LookAndFeel {

	private final Map<Class<? extends Component>, ComponentUIFactory> registeredComponentUIs;

	public LookAndFeelImp(Map<Class<? extends Component>, ComponentUIFactory> registeredComponentUIs) {
		this.registeredComponentUIs = registeredComponentUIs;
	}

	@Override
	public ComponentUI createUIFor(Component component, ComponentUI parent) {
		Class<?> componentClass = component.getPrimaryType();
		while (componentClass != null && registeredComponentUIs.get(componentClass) == null) {
			componentClass = componentClass.getSuperclass();
		}
		if (componentClass == null) {
			return null;
		}
		if (!Component.class.isAssignableFrom(componentClass)) {
			return null;
		}
		
		return registeredComponentUIs
			.get(componentClass)
			.create(component, parent);
	}
	
}
