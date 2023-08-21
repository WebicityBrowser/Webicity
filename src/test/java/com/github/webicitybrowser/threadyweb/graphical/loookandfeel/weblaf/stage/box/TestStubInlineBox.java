package com.github.webicitybrowser.threadyweb.graphical.loookandfeel.weblaf.stage.box;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.CloneBox;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.box.InlineBoxChildrenTracker;

public class TestStubInlineBox implements CloneBox {

	private BoxChildrenTracker childrenTracker = new InlineBoxChildrenTracker(this);

	@Override
	public BoxChildrenTracker getChildrenTracker() {
		return childrenTracker;
	}

	@Override
	public CloneBox cloneEmpty() {
		return new TestStubInlineBox();
	}

	@Override
	public UIDisplay<?, ?, ?> display() {
		throw new UnsupportedOperationException("Unimplemented method 'display'");
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
