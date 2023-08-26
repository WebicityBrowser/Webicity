package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.solid;

import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.SolidLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayUnit;

public final class FlowSolidRenderer {
	
	private static final SolidLayoutManager INNER_LAYOUT_MANAGER = new FlowLayoutManagerImp();
	
	private FlowSolidRenderer() {}

	public static InnerDisplayUnit render(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		LayoutResult layoutResult = INNER_LAYOUT_MANAGER.render(box, globalRenderContext, localRenderContext);
		InnerDisplayUnit renderedUnit = new InnerDisplayUnit(box.display(), layoutResult.fitSize(), layoutResult.childLayoutResults());
		return renderedUnit;
	}
	
}
