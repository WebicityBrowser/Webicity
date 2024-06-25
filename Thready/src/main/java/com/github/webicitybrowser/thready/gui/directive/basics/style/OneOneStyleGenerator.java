package com.github.webicitybrowser.thready.gui.directive.basics.style;

import com.github.webicitybrowser.thready.gui.directive.basics.ChildrenDirective;
import com.github.webicitybrowser.thready.gui.directive.basics.pool.NestingDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.pool.ComposedDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePoolListener;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.directive.GraphicalDirective;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;

public class OneOneStyleGenerator implements StyleGenerator {

	private final DirectivePool styleDirectives;

	public OneOneStyleGenerator(ComponentUI componentUI, DirectivePool parentDirectives) {
		DirectivePool componentDirectives = componentUI.getComponent().getStyleDirectives();
		this.styleDirectives = composeStyleDirectives(parentDirectives, componentDirectives);
		styleDirectives.addEventListener(createEventListener(componentUI));
	}

	@Override
	public StyleGenerator[] createChildStyleGenerators(ComponentUI[] children) {
		StyleGenerator[] childStyleGenerators = new StyleGenerator[children.length];
		for (int i = 0; i < children.length; i++) {
			childStyleGenerators[i] = new OneOneStyleGenerator(children[i], styleDirectives);
		}
		
		return childStyleGenerators;
	}

	@Override
	public DirectivePool getStyleDirectives() {
		return styleDirectives;
	}
	
	private DirectivePool composeStyleDirectives(DirectivePool parentDirectives, DirectivePool componentDirectives) {
		ComposedDirectivePool<DirectivePool> styleDirectives = new NestingDirectivePool(parentDirectives);
		styleDirectives.addDirectivePool(componentDirectives);
		return styleDirectives;
	}
	
	private DirectivePoolListener createEventListener(ComponentUI componentUI) {
		return new DirectivePoolListener() {
			@Override
			public void onMassChange() {
				componentUI.invalidate(InvalidationLevel.STYLE);
			}
			
			@Override
			public void onDirective(Directive directive) {
				// TODO: Make ComponentUI responsible for handling directive changes
				if (directive instanceof GraphicalDirective graphicalDirective) {
					componentUI.invalidate(graphicalDirective.getInvalidationLevel());
				} else if (directive instanceof ChildrenDirective) {
					componentUI.invalidate(InvalidationLevel.STYLE);
				}
			}
		};
	}

}
