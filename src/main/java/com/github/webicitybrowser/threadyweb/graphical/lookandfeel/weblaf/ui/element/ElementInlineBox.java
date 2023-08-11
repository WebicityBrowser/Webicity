package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.FluidBoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoundBoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.PrerenderMessage;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayLayout;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.TextConsolidationPrerenderMessage;

public class ElementInlineBox implements ElementBox {

	private final BoundBoxChildrenTracker childrenTracker = new FluidBoxChildrenTracker();
	
	private final Component owningComponent;
	private final DirectivePool styleDirectives;
	private final InnerDisplayLayout layout;

	public ElementInlineBox(Component owningComponent, DirectivePool styleDirectives, InnerDisplayLayout layout) {
		this.owningComponent = owningComponent;
		this.styleDirectives = styleDirectives;
		this.layout = layout;
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
			childrenTracker.getChildren().forEach(child -> child.getRaw().message(message));
		} else {
			message.handleDefault(this);
		}
	}

}
