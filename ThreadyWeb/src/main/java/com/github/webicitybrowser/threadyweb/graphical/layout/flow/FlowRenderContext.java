package com.github.webicitybrowser.threadyweb.graphical.layout.flow;

import java.util.function.Function;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutManagerContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.BuildableRenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.StyledUnitGenerator;

public record FlowRenderContext(
	LayoutManagerContext layoutManagerContext,
	Function<DirectivePool, BuildableRenderedUnit> buildableUnitGenerator,
	StyledUnitGenerator styledUnitGenerator,
	FlowRootContextSwitch flowRootContextSwitch
) {

	public GlobalRenderContext globalRenderContext() {
		return layoutManagerContext.globalRenderContext();
	}

	public LocalRenderContext localRenderContext() {
		return layoutManagerContext.localRenderContext();
	}
	
}
