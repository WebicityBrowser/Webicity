package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.solid;

import com.github.webicitybrowser.thready.gui.graphical.layout.base.flowing.FlowingLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutManagerContext;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.ContainerRenderedUnit;

public final class ContainerSolidRenderer {

	private ContainerSolidRenderer() {}
	
	public static ContainerRenderedUnit render(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		LayoutManagerContext layoutManagerContext = new LayoutManagerContext(
			box, box.getChildrenTracker().getChildren(),
			globalRenderContext, localRenderContext);
		LayoutResult layoutResults = new FlowingLayoutManager().render(layoutManagerContext);
		
		ContainerRenderedUnit renderedUnit = new ContainerRenderedUnit(box, localRenderContext.preferredSize(), layoutResults.childLayoutResults());
		return renderedUnit;
	}
	
}
