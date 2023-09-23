package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block;

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

public final class FlowBlockAnonRenderer {
	
	private FlowBlockAnonRenderer() {}

	public static void renderAnonBox(FlowBlockRendererState state, Box anonBox) {
		AbsoluteSize preferredSize = new AbsoluteSize(RelativeDimension.UNBOUNDED, RelativeDimension.UNBOUNDED);
		GlobalRenderContext globalRenderContext = state.getGlobalRenderContext();
		LocalRenderContext localRenderContext = state.getLocalRenderContext();
		ContextSwitch flowRootContextSwitch = state.flowContext().flowRootContextSwitch();
		LocalRenderContext childLocalRenderContext = LocalRenderContext.create(
			localRenderContext.getPreferredSize(),
			localRenderContext.getParentFontMetrics(),
			new ContextSwitch[] { flowRootContextSwitch });
		RenderedUnit childUnit = UIPipeline.render(anonBox, globalRenderContext, childLocalRenderContext);
		AbsoluteSize adjustedSize = adjustAnonSize(state, preferredSize, childUnit.fitSize());
		AbsolutePosition childPosition = state.positionTracker().addBox(adjustedSize, new float[4]);
		Rectangle childRect = new Rectangle(childPosition, adjustedSize);
		state.addChildLayoutResult(new ChildLayoutResult(childUnit, childRect));
	}

	private static AbsoluteSize adjustAnonSize(
		FlowBlockRendererState state, AbsoluteSize preferredSize, AbsoluteSize fitSize
	) {
		AbsoluteSize parentSize = state.getLocalRenderContext().getPreferredSize();
		
		float widthComponent = preferredSize.width() != RelativeDimension.UNBOUNDED ?
			preferredSize.width() :
			Math.max(parentSize.width(), fitSize.width());

		float heightComponent = preferredSize.height() != RelativeDimension.UNBOUNDED ?
			preferredSize.height() :
			fitSize.height();

		return new AbsoluteSize(widthComponent, heightComponent);
	}

}
