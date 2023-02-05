package everyos.desktop.thready.basic.directive;

import everyos.desktop.thready.basic.event.MouseListener;
import everyos.desktop.thready.core.gui.directive.Directive;

public interface ExternalMouseListenerDirective extends Directive {

	MouseListener getMouseListener();
	
	static Directive of(MouseListener mouseListener) {
		return new ExternalMouseListenerDirective() {
			
			@Override
			public Class<? extends Directive> getDirectiveClass() {
				return ExternalMouseListenerDirective.class;
			}
			
			@Override
			public MouseListener getMouseListener() {
				return mouseListener;
			}
			
		};
	}

	
	
}
