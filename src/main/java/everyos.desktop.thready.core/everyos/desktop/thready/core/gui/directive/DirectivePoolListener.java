package everyos.desktop.thready.core.gui.directive;

import javax.lang.model.element.ModuleElement.Directive;

public interface DirectivePoolListener {

	void onDirective(Directive directive);
	
	void onDelete();
	
}
