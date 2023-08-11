package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayLayout;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.FlowBlockRenderer;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.inline.FlowInlineRenderer;

public class FlowInnerDisplayLayout implements InnerDisplayLayout {

	@Override
	public RenderedUnitGenerator<InnerDisplayUnit> renderBox(ChildrenBox box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		if (box.isFluid()) {
			return FlowInlineRenderer.render(box, renderContext, localRenderContext);
		} else {
			return FlowBlockRenderer.render(box, renderContext, localRenderContext);
		}
	}

}
