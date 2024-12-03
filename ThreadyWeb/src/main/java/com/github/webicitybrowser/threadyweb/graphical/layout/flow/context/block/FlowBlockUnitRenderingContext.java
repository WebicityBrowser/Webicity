package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block;

import java.util.function.Function;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.BoxOffsetDimensions;

public record FlowBlockUnitRenderingContext(
	Box childBox, BoxOffsetDimensions renderParameters,
	Function<AbsoluteSize, LocalRenderContext> localRenderContextGenerator,
	Function<AbsoluteSize, AbsoluteSize> childSizeGenerator
) {
	
}
