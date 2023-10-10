package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block;

import java.util.List;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.BasicAnonymousFluidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block.floatbox.FlowBlockFloatProcessor;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block.floatbox.FlowBlockFloatRenderer;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util.FlowUtils;

public final class FlowBlockRenderer {
	
	private FlowBlockRenderer() {}

	public static LayoutResult render(FlowRenderContext context) {
		Font2D font = FlowUtils.computeFont(
			context.layoutManagerContext(),
			context.layoutManagerContext().layoutDirectives(),
			context.localRenderContext().getParentFontMetrics());
		FlowBlockRendererState state = new FlowBlockRendererState(context, font);
		renderChildren(state, context.layoutManagerContext().children());

		return LayoutResult.create(state.childLayoutResults(), state.positionTracker().fitSize());
	}

	private static void renderChildren(FlowBlockRendererState state, List<Box> children) {
		int nonFloatOffset = FlowBlockFloatProcessor.renderInitialFloats(state, children);
		for (int i = nonFloatOffset; i < children.size(); i++) {
			Box childBox = children.get(i);
			if (childBox instanceof BasicAnonymousFluidBox) {
				FlowBlockFloatProcessor.collectPostFloats(state, children, i + 1);
				FlowBlockAnonRenderer.renderAnonBox(state, childBox);
			} else if (FlowBlockFloatRenderer.isFloatBox(childBox)) {
				continue;
			} else {
				FlowBlockFloatProcessor.collectPostFloats(state, children, i + 1);
				FlowBlockBlockRenderer.renderChild(state, childBox);
			}
			FlowBlockFloatProcessor.addRemainingFloats(state);
		}
	}

}
