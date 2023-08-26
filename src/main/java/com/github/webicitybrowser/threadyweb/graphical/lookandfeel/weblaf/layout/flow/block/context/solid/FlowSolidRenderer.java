package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.solid;

import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayUnit;

public final class FlowSolidRenderer {
	
	private FlowSolidRenderer() {}

	public static InnerDisplayUnit render(
		ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext, UIDisplay<?, ?, InnerDisplayUnit> innerDisplay
	) {
		LayoutResult layoutResult = new FlowLayoutManagerImp(innerDisplay).render(box, globalRenderContext, localRenderContext);
		InnerDisplayUnit renderedUnit = InnerDisplayUnit.create(box.display(), layoutResult.fitSize(), layoutResult.childLayoutResults());
		return renderedUnit;
	}
	
}
