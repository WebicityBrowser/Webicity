package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.fluid;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public class HorizontalFluidLines implements FluidLines {

	private final GlobalRenderContext globalRenderContext;
	private final AbsoluteSize maxBounds;
	private final ContextSwitch[] switches;
	
	private List<ChildLayoutResult> rendered = new ArrayList<>();
	private float posX = 0;
	private float posY = 0;
	private float totalWidth = 0;
	private float curLineHeight = 0;

	public HorizontalFluidLines(GlobalRenderContext globalRenderContext, AbsoluteSize maxBounds) {
		this.globalRenderContext = globalRenderContext;
		this.maxBounds = maxBounds;
		this.switches = new ContextSwitch[] {};
	}

	@Override
	public void addBox(Box child) {
		// TODO: Re-add splitting functionality
		RenderedUnit unit = UIPipeline.render(child, globalRenderContext, createLocalRenderContext(switches));
		if (!(
			unit.fitSize().width() <= maxBounds.width() - posX ||
			posX == 0 ||
			unit.fitSize().width() == RelativeDimension.UNBOUNDED
		)) {
			goToNextLine();
		} else {
			addUnitToLine(unit);
		}
	}
	
	@Override
	public LayoutResult getLayoutResult() {
		ChildLayoutResult[] renderedChildren = rendered.toArray(ChildLayoutResult[]::new);
		AbsoluteSize fitSize = new AbsoluteSize(totalWidth, posY + curLineHeight);
		return LayoutResult.create(renderedChildren, fitSize);
	}
	
	private LocalRenderContext createLocalRenderContext(ContextSwitch[] switches) {
		return LocalRenderContext.create(maxBounds, switches);
	}

	private void addUnitToLine(RenderedUnit unit) {
		AbsoluteSize size = unit.fitSize();
		curLineHeight = Math.max(size.height(), curLineHeight);
		
		AbsolutePosition startPosition = new AbsolutePosition(posX, posY);
		Rectangle relativeRect = new Rectangle(startPosition, size);
		
		rendered.add(new ChildLayoutResult(unit, relativeRect));
		
		posX += size.width();
	}
	
	private void goToNextLine() {
		posX = 0;
		posY += curLineHeight;
		curLineHeight = 0;
	}
	
}
