package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayLayout;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.FlowBlockRenderer;

public class FlowInnerDisplayLayout implements InnerDisplayLayout {

	@Override
	public InnerDisplayUnit renderBox(ChildrenBox box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		if (box.isFluid()) {
			throw new UnsupportedOperationException("Fluid box self-render no longer supported");
		} else {
			return FlowBlockRenderer.render(box, renderContext, localRenderContext);
		}
	}

}
