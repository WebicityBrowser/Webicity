package com.github.webicitybrowser.threadyweb.graphical.layout.flow;

import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.SolidLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block.FlowBlockLayout;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.FlowInlineLayout;
public class FlowInnerDisplayLayout implements SolidLayoutManager {

	private final FlowConfig flowConfig;
	private SolidLayoutManager innerLayoutManager;

	public FlowInnerDisplayLayout(FlowConfig flowConfig) {
		this.flowConfig = flowConfig;
	}

	@Override
	public LayoutResult render(LayoutRenderContext layoutRenderContext) {
		List<Box> children = layoutRenderContext.treeTracker().children();
		if (children.size() > 0 && !(children.get(0).isFluid())) {
			if (!(innerLayoutManager instanceof FlowBlockLayout)) {
				innerLayoutManager = new FlowBlockLayout(flowConfig);
			}
		} else {
			if (!(innerLayoutManager instanceof FlowInlineLayout)) {
				innerLayoutManager = new FlowInlineLayout(flowConfig);
			}
		}
		
		return innerLayoutManager.render(layoutRenderContext);
	}

}
