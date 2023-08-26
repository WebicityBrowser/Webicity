package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayUnit;

public final class FlowFluidRenderer {
	
	private FlowFluidRenderer() {}

	public static InnerDisplayUnit render(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		return new InnerDisplayUnit(box.display(), new AbsoluteSize(0, 0), new ChildLayoutResult[0]);
	}

}
