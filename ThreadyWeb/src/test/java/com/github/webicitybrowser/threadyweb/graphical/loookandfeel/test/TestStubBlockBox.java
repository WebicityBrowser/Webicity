package com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.SolidBoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;

public class TestStubBlockBox implements ChildrenBox {

	private final BoxChildrenTracker childrenTracker = new SolidBoxChildrenTracker(this, null);
	private final DirectivePool styleDirectives;

	public TestStubBlockBox(DirectivePool styleDirectives) {
		this.styleDirectives = styleDirectives;
	 }

	 @Override
	public BoxChildrenTracker getChildrenTracker() {
		return childrenTracker;
	}

	@Override
	public DirectivePool styleDirectives() {
		return this.styleDirectives;
	}

	@Override
	public Context displayContext() {
		throw new UnsupportedOperationException("Unimplemented method 'displayContext'");
	}
	
}
