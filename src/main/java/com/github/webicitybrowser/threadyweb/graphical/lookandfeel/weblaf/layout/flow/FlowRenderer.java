package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow;

import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block.FlowBlockRenderer;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline.FlowInlineRenderer;

public final class FlowRenderer {

	private FlowRenderer() {}

	public static LayoutResult render(
		FlowRenderContext context
	) {
		List<Box> children = context.box().getChildrenTracker().getChildren();
		if (children.size() > 0 && !(children.get(0).isFluid())) {
			return FlowBlockRenderer.render(context);
		} else {
			return FlowInlineRenderer.render(context);
		}
	}
	
}
