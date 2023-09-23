package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block;

import java.util.List;

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
		List<Box> children = box.getChildrenTracker().getChildren();
		int nonFloatOffset = renderFloats(state, children);
		for (Box childBox: children.subList(nonFloatOffset, children.size())) {
			if (childBox instanceof BasicAnonymousFluidBox) {
				FlowBlockAnonRenderer.renderAnonBox(state, childBox);
			} else if (FlowBlockFloatRenderer.isFloatBox(childBox)) {
				continue;
			} else {
				FlowBlockBlockRenderer.renderChild(state, childBox);
			}
		}
	}

	private static int renderFloats(FlowBlockRendererState state, List<Box> children) {
		int floats = 0;
		for (int i = 0; i < children.size(); i++) {
			Box childBox = children.get(i);
			if (!FlowBlockFloatRenderer.isFloatBox(childBox)) break;
			float blockPosition = state.positionTracker().getPosition().y();
			FlowBlockFloatRenderer.addFloatBoxToLine(state, childBox, blockPosition);
			floats++;
		}

		return floats;
	}

}
