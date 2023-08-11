package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container;

import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.fluid.ContainerFluidRenderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.solid.ContainerSolidRenderer;

public final class ContainerRenderer {
	
	private ContainerRenderer() {}
	
	public static RenderedUnitGenerator<ContainerRenderedUnit> render(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		if (isStaticContext(box)) {
			return ContainerSolidRenderer.render(box, globalRenderContext, localRenderContext);
		} else {
			return ContainerFluidRenderer.render(box, globalRenderContext, localRenderContext);
		}
	}
	
	private static boolean isStaticContext(ChildrenBox box) {
		List<BoundBox<?, ?>> children = box.getChildrenTracker().getChildren();
		return children.size() == 0 || !(children.get(0).getRaw().isFluid());
	}

}
