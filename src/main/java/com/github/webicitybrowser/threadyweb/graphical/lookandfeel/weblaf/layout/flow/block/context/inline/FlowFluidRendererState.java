package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.FlowBlockRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.contexts.LineContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.TextConsolidation;

public class FlowFluidRendererState {

	private final FlowBlockRenderContext context;
	private final LineContext lineContext;

	private final TextConsolidation textConsolidation = TextConsolidation.create();

	public FlowFluidRendererState(LineDimensionConverter dimensionConverter, FlowBlockRenderContext context) {
		this.lineContext = new LineContext(dimensionConverter, context);
		this.context = context;
	}

	public LineContext lineContext() {
		return lineContext;
	}

	public GlobalRenderContext getGlobalRenderContext() {
		return context.globalRenderContext();
	}

	public LocalRenderContext getLocalRenderContext() {
		return context.localRenderContext();
	}

	public TextConsolidation getTextConsolidation() {
		return textConsolidation;
	}

}
