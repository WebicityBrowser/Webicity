package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box;

import com.github.webicitybrowser.thready.gui.directive.basics.pool.NestingDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;

public class BasicAnonymousFluidBox implements ChildrenBox {

	private final Context displayContext;
	private final UIDisplay<?, ?, ?> display;

	private final BoxChildrenTracker childrenTracker = new FluidBoxChildrenTracker(this);

	public BasicAnonymousFluidBox(Context displayContext, UIDisplay<?, ?, ?> uiDisplay) {
		this.displayContext = displayContext;
		this.display = uiDisplay;
	}

	@Override
	@Deprecated
	public DirectivePool styleDirectives() {
		return new NestingDirectivePool(displayContext.styleDirectives());
	}

	@Override
	public Context displayContext() {
		return displayContext;
	}

	@Override
	public UIDisplay<?, ?, ?> display() {
		return display;
	}

	@Override
	public BoxChildrenTracker getChildrenTracker() {
		return childrenTracker;
	}
	
	@Override
	public boolean isFluid() {
		return false;
	}

}
