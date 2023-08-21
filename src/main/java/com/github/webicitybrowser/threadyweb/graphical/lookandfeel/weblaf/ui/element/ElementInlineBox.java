package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.FluidBoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.CloneBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.PrerenderMessage;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayLayout;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.box.InlineBoxChildrenTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.TextConsolidationPrerenderMessage;

public class ElementInlineBox implements ElementBox, CloneBox {

	private final BoxChildrenTracker childrenTracker = new InlineBoxChildrenTracker(this);
	
	private final UIDisplay<?, ?, ?> display;
	private final Component owningComponent;
	private final DirectivePool styleDirectives;
	private final InnerDisplayLayout layout;

	public ElementInlineBox(UIDisplay<?, ?, ?> display, Component owningComponent, DirectivePool styleDirectives, InnerDisplayLayout layout) {
		this.display = display;
		this.owningComponent = owningComponent;
		this.styleDirectives = styleDirectives;
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
	
	@Override
	public boolean isFluid() {
		return true;
	}

	@Override
	public void message(PrerenderMessage message) {
		if (message instanceof TextConsolidationPrerenderMessage) {
			childrenTracker.getChildren().forEach(child -> child.message(message));
		} else {
			message.handleDefault(this);
		}
	}

	@Override
	public CloneBox cloneEmpty() {
		return new ElementInlineBox(display, owningComponent, styleDirectives, layout);
	}

}
