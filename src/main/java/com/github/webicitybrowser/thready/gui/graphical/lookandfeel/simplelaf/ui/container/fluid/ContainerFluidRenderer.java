package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.fluid;

import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.render.unit.SingleRenderedUnitGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.ContainerRenderedUnit;

public final class ContainerFluidRenderer {
	
	private ContainerFluidRenderer() {}

	public static RenderedUnitGenerator<ContainerRenderedUnit> render(ChildrenBox box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		List<BoundBox<?, ?>> children = box.getChildrenTracker().getChildren();
		FluidLines lines = new HorizontalFluidLines(renderContext, localRenderContext.getPreferredSize());
		renderChildren(children, lines);
		
		LayoutResult layoutResult = lines.getLayoutResult();
		ContainerRenderedUnit renderedUnit = new ContainerRenderedUnit(box, layoutResult.fitSize(), layoutResult.childLayoutResults());
		return new SingleRenderedUnitGenerator<>(renderedUnit);
	}
	
	private static void renderChildren(List<BoundBox<?, ?>> children, FluidLines lines) {
		for (BoundBox<?, ?> child: children) {
			lines.addBox(child);
		}
	}

}
