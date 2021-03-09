package everyos.engine.ribbon.ui.simple.helper;

import java.lang.reflect.InvocationTargetException;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.ComponentUIFactory;

public class ReflectiveFactory implements ComponentUIFactory {
	private Class<? extends ComponentUI> componentClass;

	public ReflectiveFactory(Class<? extends ComponentUI> componentClass) {
		this.componentClass = componentClass;
	}
	
	@Override
	public ComponentUI create(Component component, ComponentUI parent) {
		try {
			return componentClass.getDeclaredConstructor(Component.class, ComponentUI.class).newInstance(component, parent);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
			| NoSuchMethodException | SecurityException e) {
			
			throw new RuntimeException(e);
		}
	}
}
