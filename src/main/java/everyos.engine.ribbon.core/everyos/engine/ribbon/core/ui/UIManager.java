package everyos.engine.ribbon.core.ui;

import java.util.HashMap;

import everyos.engine.ribbon.core.component.Component;

public class UIManager<T extends ComponentUI> extends HashMap<Class<? extends Component>, T> {
	private static final long serialVersionUID = 1418973899929833339L;
	@SuppressWarnings("unchecked")
	public T get(Component c, ComponentUI parent) {
		Class<?> cz = c.getClass();
		while (cz!=null&&get(cz)==null) {
			cz=cz.getSuperclass();
		}
		if(cz==null) return null;
		if (!Component.class.isAssignableFrom(cz)) return null;
		return (T) get(cz).create(c, parent);
	}
}
