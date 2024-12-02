package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.SolidBoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;

public class ContainerBox implements ChildrenBox {

	private final UIDisplay<?, ?, ?> display;
	private final ComponentUI componentUI;
	private final DirectivePool styleDirectives;
	
	private final BoxChildrenTracker childTracker;
	
	public ContainerBox(UIDisplay<?, ?, ?> display, ComponentUI componentUI, DirectivePool styleDirectives, UIDisplay<?, ChildrenBox, ?> anonDisplay) {
		this.display = display;
		this.componentUI = componentUI;
		this.styleDirectives = styleDirectives;
		this.childTracker = new SolidBoxChildrenTracker(this, anonDisplay);
	}
	
	@Override
	public UIDisplay<?, ?, ?> display() {
		return this.display;
	}

	@Override
	public ComponentUI componentUI() {
		return this.componentUI;
	}

	@Override
	public DirectivePool styleDirectives() {
		return this.styleDirectives;
	}

	@Override
	public BoxChildrenTracker getChildrenTracker() {
		return childTracker;
	}

}
