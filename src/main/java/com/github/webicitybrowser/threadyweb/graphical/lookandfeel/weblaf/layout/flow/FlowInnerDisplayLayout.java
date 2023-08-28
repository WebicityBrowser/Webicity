package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow;

import java.util.function.BiFunction;
import java.util.function.Function;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayLayout;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.FlowBlockRenderer;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.FlowBlockRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.BuildableRenderedUnit;

public class FlowInnerDisplayLayout implements InnerDisplayLayout {

	private final BiFunction<LayoutResult, DirectivePool, RenderedUnit> anonBoxGenerator;
	private final Function<DirectivePool, BuildableRenderedUnit> innerUnitGenerator;

	public FlowInnerDisplayLayout(
		BiFunction<LayoutResult, DirectivePool, RenderedUnit> anonBoxGenerator,
		Function<DirectivePool, BuildableRenderedUnit> innerUnitGenerator
	) {
		this.anonBoxGenerator = anonBoxGenerator;
		this.innerUnitGenerator = innerUnitGenerator;
	}

	@Override
	public LayoutResult renderBox(ChildrenBox box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		if (box.isFluid() && box.managesSelf()) {
			throw new UnsupportedOperationException("Fluid box self-render no longer supported");
		} else {
			return FlowBlockRenderer.render(new FlowBlockRenderContext(
				box, renderContext, localRenderContext, anonBoxGenerator, innerUnitGenerator
			));
		}
	}

}
