package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block;

import java.util.function.BiFunction;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;

public record FlowBlockUnitRenderingContext(
	FlowBlockRendererState state, Box childBox, FlowBlockRenderParameters renderParameters,
	BiFunction<FlowBlockRendererState, AbsoluteSize, LocalRenderContext> localRenderContextGenerator,
	BiFunction<FlowBlockRendererState, AbsoluteSize, AbsoluteSize> childSizeGenerator
) {
	
}
