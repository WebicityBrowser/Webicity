package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowConfig;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor.LineDimension.LineDirection;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.TextConsolidation;

public class FlowInlineRenderContext {

	private final FlowConfig flowConfig;
	private final FlowRenderContext renderContext;
	private final LineBoxContainer lineContext;

	private final TextConsolidation textConsolidation = TextConsolidation.create();

	public FlowInlineRenderContext(FlowConfig flowConfig, FlowRenderContext renderContext, LineDirection lineDirection) {
		this.flowConfig = flowConfig;
		this.renderContext = renderContext;
		this.lineContext = new LineBoxContainer(this, lineDirection);
		FlowInlineRendererUtil.startNewLine(this);
	}

	public FlowConfig flowConfig() {
		return flowConfig;
	}

	public FlowRenderContext flowContext() {
		return renderContext;
	}

	public LineBoxContainer lineContext() {
		return lineContext;
	}

	public GlobalRenderContext getGlobalRenderContext() {
		return renderContext.globalRenderContext();
	}

	public LocalRenderContext getLocalRenderContext() {
		return renderContext.localRenderContext();
	}

	public DirectivePool getStyleDirectives() {
		return renderContext.layoutRenderContext().layoutDirectives();
	}

	public TextConsolidation getTextConsolidation() {
		return textConsolidation;
	}

}
