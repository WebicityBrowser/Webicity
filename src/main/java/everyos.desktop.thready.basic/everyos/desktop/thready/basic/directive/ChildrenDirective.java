package everyos.desktop.thready.basic.directive;

import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.directive.Directive;

public interface ChildrenDirective extends Directive {

	Component[] getChildren();

	public static Directive of(Component[] children) {
		return new ChildrenDirective() {
			
			@Override
			public Class<? extends Directive> getDirectiveClass() {
				return ChildrenDirective.class;
			}
			
			@Override
			public Component[] getChildren () {
				return children;
			}
			
		};
	}
	
}
