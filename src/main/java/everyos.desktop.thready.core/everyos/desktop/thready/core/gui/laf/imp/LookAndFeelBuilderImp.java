package everyos.desktop.thready.core.gui.laf.imp;

import java.util.HashMap;
import java.util.Map;

import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.laf.ComponentUIFactory;
import everyos.desktop.thready.core.gui.laf.LookAndFeel;
import everyos.desktop.thready.core.gui.laf.LookAndFeelBuilder;

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
