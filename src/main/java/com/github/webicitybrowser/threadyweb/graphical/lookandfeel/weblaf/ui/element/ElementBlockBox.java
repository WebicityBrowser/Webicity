package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.SolidBoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayLayout;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebDefaults;

public class ElementBlockBox implements ElementBox {
	
	private final UIDisplay<?, ?, ?> display;
	private final Component owningComponent;
	private final DirectivePool styleDirectives;
	private final BoxChildrenTracker childrenTracker;
	private final InnerDisplayLayout layout;

	public ElementBlockBox(UIDisplay<?, ?, ?> display, Component owningComponent, DirectivePool styleDirectives, InnerDisplayLayout layout) {
		this.display = display;
		this.owningComponent = owningComponent;
		this.styleDirectives = styleDirectives;
		this.childrenTracker = new SolidBoxChildrenTracker(this, WebDefaults.INLINE_DISPLAY);
		this.layout = layout;
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
	
	@Override
	public InnerDisplayLayout layout() {
		return this.layout;
	}

}
