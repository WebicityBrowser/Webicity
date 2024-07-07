package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block;

import java.util.function.BiFunction;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.util.BoxOffsetDimensions;

public record FlowBlockUnitRenderingContext(
	FlowBlockRenderContext state, Box childBox, BoxOffsetDimensions renderParameters,
	BiFunction<FlowBlockRenderContext, AbsoluteSize, LocalRenderContext> localRenderContextGenerator,
	BiFunction<FlowBlockRenderContext, AbsoluteSize, AbsoluteSize> childSizeGenerator
) {
	
}
