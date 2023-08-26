package com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.SolidBoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class TestStubChildrenBox implements ChildrenBox {

	private final BoxChildrenTracker childrenTracker = new SolidBoxChildrenTracker(this, null);

	@Override
	public BoxChildrenTracker getChildrenTracker() {
		return childrenTracker;
	}

	@Override
	public UIDisplay<?, ?, ?> display() {
		return null;
	}

	@Override
	public Component owningComponent() {
		throw new UnsupportedOperationException("Unimplemented method 'owningComponent'");
	}

	@Override
	public DirectivePool styleDirectives() {
		throw new UnsupportedOperationException("Unimplemented method 'styleDirectives'");
	}
	
}
