package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.stage.style;

import com.github.webicitybrowser.thready.gui.directive.basics.pool.NestingDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.pool.ComposedDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePoolListener;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleInvalidationUtil;

public class SimpleStylePoolGenerator {
	
	private final ComponentUI owner;
	private final DirectivePool primaryPool;
	
	private ComposedDirectivePool<DirectivePool> combinedPool;

	public SimpleStylePoolGenerator(ComponentUI owner, DirectivePool primaryPool) {
		this.owner = owner;
		this.primaryPool = primaryPool;
	}

	public DirectivePool createStylePool(DirectivePool parentPool, StyleGenerator styleGenerator) {
		if (combinedPool != null) {
			combinedPool.release();
		}
		combinedPool = new NestingDirectivePool(parentPool);
		combinedPool.addDirectivePool(primaryPool);
		for (DirectivePool pool: styleGenerator.getDirectivePools()) {
			combinedPool.addDirectivePool(pool);
		}
		
		combinedPool.addEventListener(new InvalidationListener());
		
		return combinedPool;
	}
	
	private class InvalidationListener implements DirectivePoolListener {
		@Override
		public void onMassChange() {
			owner.invalidate(InvalidationLevel.BOX);
		}
		
		@Override
		public void onDirective(Directive directive) {
			owner.invalidate(SimpleInvalidationUtil.getInvalidationLevelFor(directive));
		}
	}

}
