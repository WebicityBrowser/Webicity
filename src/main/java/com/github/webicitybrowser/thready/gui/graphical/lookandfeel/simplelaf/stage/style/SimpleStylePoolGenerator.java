package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.stage.style;

import com.github.webicitybrowser.thready.gui.directive.basics.pool.NestingDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.ComposedDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.StyleGenerator;

public class SimpleStylePoolGenerator {
	
	private final DirectivePool primaryPool;

	public SimpleStylePoolGenerator(DirectivePool primaryPool) {
		this.primaryPool = primaryPool;
	}

	public DirectivePool createStylePool(DirectivePool parentPool, StyleGenerator styleGenerator) {
		ComposedDirectivePool<DirectivePool> directivePool = new NestingDirectivePool(parentPool);
		directivePool.addDirectivePool(primaryPool);
		for (DirectivePool pool: styleGenerator.getDirectivePools()) {
			directivePool.addDirectivePool(pool);
		}
		
		return directivePool;
	}

}
