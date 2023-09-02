package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline.contexts.LineContext;

public final class FlowInlineRendererUtil {
	
	private FlowInlineRendererUtil() {}

	public static void startNewLineIfNotFits(FlowInlineRendererState state, AbsoluteSize preferredSize) {
		LineContext lineContext = state.lineContext();
		LineBox currentLine = lineContext.currentLine();
		LocalRenderContext localRenderContext = state.getLocalRenderContext();
		if (!currentLine.canFit(preferredSize, localRenderContext.getPreferredSize())) {
			lineContext.startNewLine();
		}
	}

}
