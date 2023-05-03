package com.github.webicitybrowser.thready.gui.directive.core.pool;

public interface ComposedDirectivePool<T extends DirectivePool>  extends DirectivePool {

	void addDirectivePool(T pool);
	
	void removeDirectivePool(T pool);
	
	// Includes self
	T[] getCurrentDirectivePools();

	void release();
	
	// Note: A composed directive pool's own set of directives typically
	// take priority over those of any other pools added to it,
	// with few exceptions
	
}
