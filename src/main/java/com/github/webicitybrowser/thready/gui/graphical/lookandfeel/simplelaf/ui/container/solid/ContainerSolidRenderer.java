package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.solid;

import com.github.webicitybrowser.thready.gui.graphical.layout.base.flowing.FlowingLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.render.unit.SingleRenderedUnitGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.ContainerRenderedUnit;

public final class ContainerSolidRenderer {

	private ContainerSolidRenderer() {}
	
	public static RenderedUnitGenerator<ContainerRenderedUnit> render(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		LayoutResult layoutResults = new FlowingLayoutManager().render(box, globalRenderContext, localRenderContext);
		
		ContainerRenderedUnit renderedUnit = new ContainerRenderedUnit(box, localRenderContext.getPreferredSize(), layoutResults.childLayoutResults());
		return new SingleRenderedUnitGenerator<>(renderedUnit);
	}
	
}
