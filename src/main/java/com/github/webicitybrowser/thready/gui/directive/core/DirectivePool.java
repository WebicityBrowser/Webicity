package com.github.webicitybrowser.thready.gui.directive.core;

import java.util.Optional;

public interface DirectivePool {

	DirectivePool directive(Directive directive);

	<T extends Directive> Optional<T> getDirectiveOrEmpty(Class<T> directiveCls);
	
}
