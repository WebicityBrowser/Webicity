package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.fluid;

import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.ContainerRenderedUnit;

public final class ContainerFluidRenderer {
	
	private ContainerFluidRenderer() {}

	public static ContainerRenderedUnit render(ChildrenBox box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		List<Box> children = box.getChildrenTracker().getChildren();
		FluidLines lines = new HorizontalFluidLines(renderContext, localRenderContext.preferredSize());
		renderChildren(children, lines);
		
		LayoutResult layoutResult = lines.getLayoutResult();
		ContainerRenderedUnit renderedUnit = new ContainerRenderedUnit(box, layoutResult.fitSize(), layoutResult.childLayoutResults());
		return renderedUnit;
	}
	
	private static void renderChildren(List<Box> children, FluidLines lines) {
		for (Box child: children) {
			lines.addBox(child);
		}
	}

}
