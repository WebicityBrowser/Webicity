package com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.SolidBoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class TestStubChildrenBox implements ChildrenBox {

	private final BoxChildrenTracker childrenTracker = new SolidBoxChildrenTracker(this, null);
	private final DirectivePool styleDirectives;
	private final UIDisplay<?, ?, ?> display;

	public TestStubChildrenBox(DirectivePool styleDirectives) {
		this.styleDirectives = styleDirectives;
		this.display = null;
	}

	public TestStubChildrenBox(DirectivePool styleDirectives, UIDisplay<?, ?, ?> display) {
		this.styleDirectives = styleDirectives;
		this.display = display;
	}

	@Override
	public BoxChildrenTracker getChildrenTracker() {
		return childrenTracker;
	}

	@Override
	public DirectivePool styleDirectives() {
		return styleDirectives;
	}

	@Override
	public UIDisplay<?, ?, ?> display() {
		return display;
	}

	@Override
	public Component owningComponent() {
		throw new UnsupportedOperationException("Unimplemented method 'owningComponent'");
	}
	
}
