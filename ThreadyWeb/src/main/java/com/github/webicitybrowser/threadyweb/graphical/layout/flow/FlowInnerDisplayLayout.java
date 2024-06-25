package com.github.webicitybrowser.threadyweb.graphical.layout.flow;

import java.util.function.Function;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutManagerContext;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.SolidLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.imp.FloatContextImp;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.imp.FloatTrackerImp;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.BuildableRenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.StyledUnitGenerator;

public class FlowInnerDisplayLayout implements SolidLayoutManager {

	private final Function<DirectivePool, BuildableRenderedUnit> innerUnitGenerator;
	private final StyledUnitGenerator styledUnitGenerator;

	public FlowInnerDisplayLayout(
		Function<DirectivePool, BuildableRenderedUnit> innerUnitGenerator,
		StyledUnitGenerator styledUnitGenerator
	) {
		this.innerUnitGenerator = innerUnitGenerator;
		this.styledUnitGenerator = styledUnitGenerator;
	}

	@Override
	public LayoutResult render(LayoutManagerContext layoutManagerContext) {
		return FlowRenderer.render(createFlowRenderContext(layoutManagerContext));
	}

	private FlowRenderContext createFlowRenderContext(LayoutManagerContext layoutManagerContext) {
		LocalRenderContext localRenderContext = layoutManagerContext.localRenderContext();
		FlowRootContextSwitch flowRootContextSwitch = getFlowRootContextSwitch(localRenderContext);

		return new FlowRenderContext(
			layoutManagerContext, innerUnitGenerator, styledUnitGenerator, flowRootContextSwitch
		);
	}

	private FlowRootContextSwitch getFlowRootContextSwitch(LocalRenderContext localRenderContext) {
		for (ContextSwitch contextSwitch: localRenderContext.contextSwitches()) {
			if (contextSwitch instanceof FlowRootContextSwitch flowRootContextSwitch) {
				return flowRootContextSwitch;
			}
		}

		AbsolutePosition predictedPosition = AbsolutePosition.ZERO_POSITION;
		FloatContextImp floatContext = new FloatContextImp(new FloatTrackerImp());
		return new FlowRootContextSwitch(predictedPosition, floatContext);
	}

}
