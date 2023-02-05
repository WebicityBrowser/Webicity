package everyos.desktop.thready.basic.directive;

import everyos.desktop.thready.basic.event.MouseListener;
import everyos.desktop.thready.core.gui.directive.Directive;

public interface MouseListenerDirective extends Directive {

	MouseListener getMouseListener();
	
	static Directive of(MouseListener mouseListener) {
		return new MouseListenerDirective() {
			
			@Override
			public Class<? extends Directive> getDirectiveClass() {
				return MouseListenerDirective.class;
			}
			
			@Override
			public MouseListener getMouseListener() {
				return mouseListener;
			}
			
		};
	}

	
	
}
