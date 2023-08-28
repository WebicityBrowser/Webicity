package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block;

import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.FlowBlockRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.FlowFluidRenderer;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.solid.FlowSolidRenderer;

public final class FlowBlockRenderer {

	private FlowBlockRenderer() {}

	public static LayoutResult render(
		FlowBlockRenderContext context
	) {
		List<Box> children = context.box().getChildrenTracker().getChildren();
		if (children.size() > 0 && !(children.get(0).isFluid())) {
			return FlowSolidRenderer.render(context);
		} else {
			return FlowFluidRenderer.render(context);
		}
	}
	
}
