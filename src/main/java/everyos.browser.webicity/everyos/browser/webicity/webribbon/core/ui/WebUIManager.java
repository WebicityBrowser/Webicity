package everyos.browser.webicity.webribbon.core.ui;

import java.util.HashMap;

import everyos.browser.webicity.webribbon.core.component.WebComponent;

public class WebUIManager<T extends WebComponentUI> extends HashMap<Class<? extends WebComponent>, T> {
	private static final long serialVersionUID = 5772884045812272682L;

	@SuppressWarnings("unchecked")
	public T get(WebComponent c, WebComponentUI parent) {
		Class<?> cz = c.getClass();
		while (cz!=null&&get(cz)==null) {
			cz=cz.getSuperclass();
		}
		if(cz==null) return null;
		if (!WebComponent.class.isAssignableFrom(cz)) return null;
		return (T) get(cz).create(c, parent);
	}
}
