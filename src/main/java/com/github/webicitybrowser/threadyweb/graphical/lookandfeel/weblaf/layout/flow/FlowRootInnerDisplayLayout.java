package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayLayout;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.FlowBlockRenderer;

public class FlowRootInnerDisplayLayout implements InnerDisplayLayout {

	private final UIDisplay<?, ?, InnerDisplayUnit> innerDisplay;

	public FlowRootInnerDisplayLayout(UIDisplay<?, ?, InnerDisplayUnit> innerDisplay) {
		this.innerDisplay = innerDisplay;
	}

	@Override
	public InnerDisplayUnit renderBox(ChildrenBox box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		return FlowBlockRenderer.render(box, renderContext, localRenderContext, innerDisplay);
	}

}
