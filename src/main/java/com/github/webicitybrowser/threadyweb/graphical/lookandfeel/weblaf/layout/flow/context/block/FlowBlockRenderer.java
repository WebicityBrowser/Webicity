package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.BasicAnonymousFluidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util.FlowUtils;

public final class FlowBlockRenderer {
	
	private FlowBlockRenderer() {}

	public static LayoutResult render(FlowRenderContext context) {
		Font2D font = FlowUtils.computeFont(
			context,
			context.box().styleDirectives(),
			context.localRenderContext().getParentFontMetrics());
		FlowBlockRendererState state = new FlowBlockRendererState(context, font);
		renderChildren(state, context.box());

		return LayoutResult.create(state.childLayoutResults(), state.positionTracker().fitSize());
	}

	private static void renderChildren(FlowBlockRendererState state, ChildrenBox box) {
		for (Box childBox: box.getChildrenTracker().getChildren()) {
			if (childBox instanceof BasicAnonymousFluidBox) {
				FlowBlockAnonRenderer.renderAnonBox(state, childBox);
			} else {
				FlowBlockBlockRenderer.renderChild(state, childBox);
			}
		}
	}

}
