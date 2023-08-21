package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.SolidBoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoundBoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class ContainerBox implements ChildrenBox {

	private final UIDisplay<?, ?, ?> display;
	private final Component owningComponent;
	private final DirectivePool styleDirectives;
	
	private final BoundBoxChildrenTracker childTracker;
	
	public ContainerBox(UIDisplay<?, ?, ?> display, Component owningComponent, DirectivePool styleDirectives, UIDisplay<?, ChildrenBox, ?> anonDisplay) {
		this.display = display;
		this.owningComponent = owningComponent;
		this.styleDirectives = styleDirectives;
		this.childTracker = new SolidBoxChildrenTracker(this, anonDisplay);
	}
	
	@Override
	public UIDisplay<?, ?, ?> display() {
		return this.display;
	}

	@Override
	public Component owningComponent() {
		return this.owningComponent;
	}

	@Override
	public DirectivePool styleDirectives() {
		return this.styleDirectives;
	}

	@Override
	public BoundBoxChildrenTracker getChildrenTracker() {
		return childTracker;
	}

}
