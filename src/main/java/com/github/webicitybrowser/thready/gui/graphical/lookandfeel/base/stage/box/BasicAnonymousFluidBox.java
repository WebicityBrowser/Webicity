package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoundBoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.PrerenderMessage;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class BasicAnonymousFluidBox implements ChildrenBox {

	private final UIDisplay<?, ?, ?> display;
	private final Component owningComponent;
	private final DirectivePool styleDirectives;

	private final BoundBoxChildrenTracker childrenTracker = new FluidBoxChildrenTracker(this);

	public BasicAnonymousFluidBox(UIDisplay<?, ?, ?> display, Component owningComponent, DirectivePool styleDirectives) {
		this.display = display;
		this.owningComponent = owningComponent;
		this.styleDirectives = styleDirectives;
	}

	@Override
	public UIDisplay<?, ?, ?> display() {
		return display;
	}

	@Override
	public Component owningComponent() {
		return owningComponent;
	}

	@Override
	public DirectivePool styleDirectives() {
		return styleDirectives;
	}

	@Override
	public BoundBoxChildrenTracker getChildrenTracker() {
		return childrenTracker;
	}
	
	@Override
	public boolean isFluid() {
		return false;
	}

	@Override
	public void message(PrerenderMessage message) {
		getChildrenTracker().getChildren().forEach(child -> {
			child.message(message);
		});
	}

}
