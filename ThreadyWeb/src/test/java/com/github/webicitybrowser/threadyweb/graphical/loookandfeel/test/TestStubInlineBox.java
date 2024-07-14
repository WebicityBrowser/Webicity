package com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.CloneBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowTestUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.box.InlineBoxChildrenTracker;

public class TestStubInlineBox implements CloneBox {

	private BoxChildrenTracker childrenTracker = new InlineBoxChildrenTracker(this);
	private DirectivePool styleDirectives = FlowTestUtils.createBasicDirectivePool();

	@Override
	public BoxChildrenTracker getChildrenTracker() {
		return childrenTracker;
	}

	@Override
	public CloneBox cloneEmpty() {
		return new TestStubInlineBox();
	}

	@Override
	public boolean managesSelf() {
		return false;
	}

	@Override
	public Context displayContext() {
		throw new UnsupportedOperationException("Unimplemented method 'displayContext'");
	}

	@Override
	public DirectivePool styleDirectives() {
		return styleDirectives;
	}
	
}
