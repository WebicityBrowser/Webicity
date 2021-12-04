package everyos.engine.ribbon.core.ui;

import java.util.HashMap;

import everyos.engine.ribbon.core.graphics.Component;

public class UIManager extends HashMap<Class<? extends Component>, ComponentUIFactory> {
	private static final long serialVersionUID = 1418973899929833339L;
	
	public ComponentUI get(Component c, ComponentUI parent) {
		Class<?> cz = c.getClass();
		while (cz != null && get(cz) == null) {
			cz=cz.getSuperclass();
		}
		if (cz == null) {
			return null;
		}
		if (!Component.class.isAssignableFrom(cz)) {
			return null;
		}
		
		return get(cz).create(c, parent);
	}
}
