package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.solid;

import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.FlowBlockRenderContext;

public final class FlowSolidRenderer {
	
	private FlowSolidRenderer() {}

	public static LayoutResult render(FlowBlockRenderContext context) {
		return new FlowLayoutManagerImp(context.anonUnitGenerator(), context.innerUnitGenerator())
			.render(context.box(), context.globalRenderContext(), context.localRenderContext());
	}
	
}
