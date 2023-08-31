package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow;

import java.util.function.Function;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.SolidLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.BuildableRenderedUnit;

public class FlowInnerDisplayLayout implements SolidLayoutManager {

	private final Function<DirectivePool, BuildableRenderedUnit> innerUnitGenerator;

	public FlowInnerDisplayLayout(
		Function<DirectivePool, BuildableRenderedUnit> innerUnitGenerator
	) {
		this.innerUnitGenerator = innerUnitGenerator;
	}

	@Override
	public LayoutResult render(ChildrenBox box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		if (box.isFluid() && box.managesSelf()) {
			throw new UnsupportedOperationException("Fluid box self-render no longer supported");
		} else {
			return FlowRenderer.render(new FlowRenderContext(
				box, renderContext, localRenderContext, innerUnitGenerator
			));
		}
	}

}
