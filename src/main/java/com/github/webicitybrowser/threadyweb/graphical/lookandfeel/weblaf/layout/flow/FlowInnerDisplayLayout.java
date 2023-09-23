package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow;

import java.util.function.Function;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.SolidLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.floatbox.imp.FloatContextImp;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.floatbox.imp.FloatTrackerImp;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.BuildableRenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.StyledUnitGenerator;

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
	public LayoutResult render(ChildrenBox box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		return FlowRenderer.render(createFlowRenderContext(box, renderContext, localRenderContext));
	}

	private FlowRenderContext createFlowRenderContext(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		FlowRootContextSwitch flowRootContextSwitch = getFlowRootContextSwitch(localRenderContext);

		return new FlowRenderContext(
			box, globalRenderContext, localRenderContext, innerUnitGenerator, styledUnitGenerator, flowRootContextSwitch
		);
	}

	private FlowRootContextSwitch getFlowRootContextSwitch(LocalRenderContext localRenderContext) {
		for (ContextSwitch contextSwitch: localRenderContext.getContextSwitches()) {
			if (contextSwitch instanceof FlowRootContextSwitch flowRootContextSwitch) {
				return flowRootContextSwitch;
			}
		}

		AbsolutePosition predictedPosition = new AbsolutePosition(0, 0);
		FloatContextImp floatContext = new FloatContextImp(new FloatTrackerImp());
		return new FlowRootContextSwitch(predictedPosition, floatContext);
	}

}
