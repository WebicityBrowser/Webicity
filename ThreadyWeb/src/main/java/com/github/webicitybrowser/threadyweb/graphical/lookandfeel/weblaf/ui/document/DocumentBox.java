package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.document;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.SolidBoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebDefaults;

public class DocumentBox implements ChildrenBox {
	
	private final UIDisplay<?, ?, ?> display;
	private final Component owningComponent;
	private final DirectivePool styleDirectives;
	private final BoxChildrenTracker childrenTracker;

	public DocumentBox(UIDisplay<?, ?, ?> display, Component owningComponent, DirectivePool styleDirectives) {
		this.display = display;
		this.owningComponent = owningComponent;
		this.styleDirectives = styleDirectives;
		this.childrenTracker = new SolidBoxChildrenTracker(this, WebDefaults.INLINE_DISPLAY);
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
	public BoxChildrenTracker getChildrenTracker() {
		return this.childrenTracker;
	}

}
