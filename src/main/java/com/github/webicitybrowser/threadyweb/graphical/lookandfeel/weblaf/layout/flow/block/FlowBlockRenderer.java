package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block;

import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.FlowFluidRenderer;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.solid.FlowSolidRenderer;

public final class FlowBlockRenderer {

	private FlowBlockRenderer() {}

	public static RenderedUnitGenerator<InnerDisplayUnit> render(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		List<Box> children = box.getChildrenTracker().getChildren();
		if (children.size() > 0 && !(children.get(0).isFluid())) {
			return FlowSolidRenderer.render(box, globalRenderContext, localRenderContext);
		} else {
			return FlowFluidRenderer.render(box, globalRenderContext, localRenderContext);
		}
	}
	
}
