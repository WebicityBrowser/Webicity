package everyos.desktop.thready.core.gui.laf.imp;

import java.util.Map;

import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.laf.ComponentUIFactory;
import everyos.desktop.thready.core.gui.laf.LookAndFeel;
import everyos.desktop.thready.core.gui.laf.component.ComponentUI;

public class LookAndFeelImp implements LookAndFeel {

	private final Map<Class<? extends Component>, ComponentUIFactory> registeredComponentUIs;

	public LookAndFeelImp(Map<Class<? extends Component>, ComponentUIFactory> registeredComponentUIs) {
		this.registeredComponentUIs = registeredComponentUIs;
	}

	@Override
	public ComponentUI createUIFor(Component component, ComponentUI parent) {
		Class<?> componentClass = component.getClass();
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
