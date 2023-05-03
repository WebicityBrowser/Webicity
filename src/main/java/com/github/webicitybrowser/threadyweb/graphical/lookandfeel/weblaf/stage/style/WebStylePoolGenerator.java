package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.style;

import com.github.webicitybrowser.thready.gui.directive.basics.pool.NestingDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.ComposedDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;

public class WebStylePoolGenerator {
	
	private final DirectivePool primaryPool;

	public WebStylePoolGenerator(DirectivePool primaryPool) {
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
