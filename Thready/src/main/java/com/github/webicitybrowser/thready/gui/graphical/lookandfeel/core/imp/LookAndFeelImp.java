package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.imp;

import java.util.Map;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUIFactory;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleDefinition;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class LookAndFeelImp implements LookAndFeel {

	private final Map<Class<? extends Component>, ComponentUIFactory> registeredComponentUIs;
	private final Map<Class<? extends Directive>, StyleDefinition<? extends Directive>> registeredStyleDefinitions;

	public LookAndFeelImp(
		Map<Class<? extends Component>, ComponentUIFactory> registeredComponentUIs,
		Map<Class<? extends Directive>, StyleDefinition<? extends Directive>> registeredStyleDefinitions
	) {
		this.registeredComponentUIs = registeredComponentUIs;
		this.registeredStyleDefinitions = registeredStyleDefinitions;
	}

	@Override
	public ComponentUI createUIFor(Component component, ComponentUI parent) {
		Class<?> componentClass = component.getPrimaryType();
		while (componentClass != null && registeredComponentUIs.get(componentClass) == null) {
			componentClass = componentClass.getSuperclass();
		}
		
		if (componentClass == null || !Component.class.isAssignableFrom(componentClass)) {
			throw new RuntimeException("No component UI available for " + component);
		}
		
		return registeredComponentUIs
			.get(componentClass)
			.create(component, parent);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Directive> StyleDefinition<T> getStyleDefinition(Class<T> directiveType) {
		if (!registeredStyleDefinitions.containsKey(directiveType)) {
			throw new RuntimeException("No style definition available for " + directiveType);
		}

		return (StyleDefinition<T>) registeredStyleDefinitions.get(directiveType);
	}
	
}
