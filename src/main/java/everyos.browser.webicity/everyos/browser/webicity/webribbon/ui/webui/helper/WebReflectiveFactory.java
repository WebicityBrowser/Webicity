package everyos.browser.webicity.webribbon.ui.webui.helper;

import java.lang.reflect.InvocationTargetException;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUIFactory;

public class WebReflectiveFactory implements WebComponentUIFactory {
	private Class<? extends WebComponentUI> componentClass;

	public WebReflectiveFactory(Class<? extends WebComponentUI> componentClass) {
		this.componentClass = componentClass;
	}
	
	@Override
	public WebComponentUI create(WebComponent component, WebComponentUI parent) {
		try {
			return componentClass.getDeclaredConstructor(WebComponent.class, WebComponentUI.class).newInstance(component, parent);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
			| NoSuchMethodException | SecurityException e) {
			
			throw new RuntimeException(e);
		}
	}
}
