package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block;

import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.SolidLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.BasicAnonymousFluidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.threadyweb.graphical.layout.adjusted.AdjustedLayout;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowConfig;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block.floatbox.FlowBlockFloatProcessor;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block.floatbox.FlowBlockFloatRenderer;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.util.FlowUtils;

public class FlowBlockLayout implements SolidLayoutManager {
	
	private final FlowConfig flowConfig;

	public FlowBlockLayout(FlowConfig flowConfig) {
		this.flowConfig = flowConfig;
	}

	public LayoutResult render(LayoutRenderContext layoutRenderContext) {
		FlowRenderContext flowRenderContext = FlowUtils.createFlowRenderContext(layoutRenderContext);
		FlowBlockRenderContext state = new FlowBlockRenderContext(flowConfig, flowRenderContext);
		renderChildren(state, layoutRenderContext);

		return LayoutResult.create(state.childLayoutResults(), state.positionTracker().fitSize());
	}

	private static void renderChildren(FlowBlockRenderContext state, LayoutRenderContext layoutRenderContext) {
		List<Box> children = layoutRenderContext.treeTracker().children();
		int nonFloatOffset = FlowBlockFloatProcessor.renderInitialFloats(state, children);
		for (int i = nonFloatOffset; i < children.size(); i++) {
			Box childBox = children.get(i);
			if (childBox instanceof BasicAnonymousFluidBox) {
				FlowBlockFloatProcessor.collectPostFloats(state, children, i + 1);
				FlowBlockAnonRenderer.renderAnonBox(state, childBox);
			} else if (!AdjustedLayout.isInFlow(childBox)) {
				ChildLayoutResult adjustedLayoutResult = AdjustedLayout.renderAdjustedBox(
					childBox, layoutRenderContext, state.flowConfig().styledUnitGenerator());
				if (adjustedLayoutResult != null) // TODO: Remove this line once all position types implemented
				state.addChildLayoutResult(adjustedLayoutResult);
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
