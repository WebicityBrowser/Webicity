package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowRootContextSwitch;

public final class FlowBlockAnonRenderer {
	
	private FlowBlockAnonRenderer() {}

	public static void renderAnonBox(FlowBlockRenderContext state, Box anonBox) {
		AbsoluteSize preferredSize = new AbsoluteSize(RelativeDimension.UNBOUNDED, RelativeDimension.UNBOUNDED);
		GlobalRenderContext globalRenderContext = state.getGlobalRenderContext();
		LocalRenderContext localRenderContext = state.getLocalRenderContext();
		LocalRenderContext childLocalRenderContext = LocalRenderContext.create(
			localRenderContext.preferredSize(),
			new ContextSwitch[] { createChildFlowRootContextSwitch(state, anonBox) });
		RenderedUnit childUnit = UIPipeline.render(anonBox, globalRenderContext, childLocalRenderContext);
		AbsoluteSize adjustedSize = adjustAnonSize(state, preferredSize, childUnit.fitSize());
		AbsolutePosition childPosition = state.positionTracker().nextBoxPosition(adjustedSize, new float[4]);
		Rectangle childRect = new Rectangle(childPosition, adjustedSize);
		state.addChildLayoutResult(new ChildLayoutResult(childUnit, childRect));
	}

	private static ContextSwitch createChildFlowRootContextSwitch(FlowBlockRenderContext state, Box anonBox) {
		FlowRootContextSwitch flowRootContextSwitch = state.flowContext().flowRootContextSwitch();
		return new FlowRootContextSwitch(
			flowRootContextSwitch.floatContext().offset(state.positionTracker().getPosition()));
	}

	private static AbsoluteSize adjustAnonSize(
		FlowBlockRenderContext state, AbsoluteSize preferredSize, AbsoluteSize fitSize
	) {
		AbsoluteSize parentSize = state.getLocalRenderContext().preferredSize();
		
		float widthComponent = preferredSize.width() != RelativeDimension.UNBOUNDED ?
			preferredSize.width() :
			Math.max(parentSize.width(), fitSize.width());

		float heightComponent = preferredSize.height() != RelativeDimension.UNBOUNDED ?
			preferredSize.height() :
			fitSize.height();

		return new AbsoluteSize(widthComponent, heightComponent);
	}

}
