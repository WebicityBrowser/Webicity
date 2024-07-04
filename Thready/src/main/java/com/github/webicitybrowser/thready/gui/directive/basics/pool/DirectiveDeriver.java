package com.github.webicitybrowser.thready.gui.directive.basics.pool;

import java.util.List;
import java.util.Optional;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;

public interface DirectiveDeriver<T extends Directive> {

	Class<T> getDerivedType();
	
	List<Class<? extends Directive>> getOwnDependencies();

	List<Class<? extends Directive>> getParentDependencies();

	Optional<T> derive(DirectivePool ownPool, DirectivePool parentPool);

}
