package everyos.desktop.thready.core.gui.component;

import everyos.desktop.thready.core.gui.InvalidationLevel;
import everyos.desktop.thready.core.gui.directive.Directive;
import everyos.desktop.thready.core.gui.directive.DirectivePool;
import everyos.desktop.thready.core.gui.laf.component.ComponentUI;

public interface Component {

	DirectivePool getDirectivePool();
	
	Component directive(Directive directive);
	
	void invalidate(InvalidationLevel level);
	
	boolean bindUI(ComponentUI ui);
	
	boolean unbindUI(ComponentUI ui);
	
}
