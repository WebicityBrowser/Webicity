package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline;

import java.util.Stack;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline.contexts.LineContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimension.LineDirection;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.TextConsolidation;

public class FlowInlineRendererState {

	private final FlowRenderContext context;
	private final LineContext lineContext;

	private final TextConsolidation textConsolidation = TextConsolidation.create();
	private final Stack<Font2D> fontStack = new Stack<>();

	public FlowInlineRendererState(LineDirection lineDirection, FlowRenderContext context) {
		this.lineContext = new LineContext(lineDirection, context);
		this.context = context;
		FlowInlineRendererUtil.startNewLine(this);
	}

	public LineContext lineContext() {
		return lineContext;
	}

	public FlowRenderContext flowContext() {
		return context;
	}

	public GlobalRenderContext getGlobalRenderContext() {
		return context.globalRenderContext();
	}

	public LocalRenderContext getLocalRenderContext() {
		return context.localRenderContext();
	}

	public DirectivePool getStyleDirectives() {
		return context.box().styleDirectives();
	}

	public TextConsolidation getTextConsolidation() {
		return textConsolidation;
	}

	public Stack<Font2D> getFontStack() {
		return fontStack;
	}

}
