package everyos.desktop.thready.basic.directive;

import everyos.desktop.thready.core.gui.SolidLayoutManager;
import everyos.desktop.thready.core.gui.directive.Directive;

public interface LayoutManagerDirective extends Directive {

	SolidLayoutManager getLayoutManager();

	public static Directive of(SolidLayoutManager layoutManager) {
		return new LayoutManagerDirective() {
			
			@Override
			public Class<? extends Directive> getDirectiveClass() {
				return LayoutManagerDirective.class;
			}
			
			@Override
			public SolidLayoutManager getLayoutManager() {
				return layoutManager;
			}
			
		};
	}
	
}
