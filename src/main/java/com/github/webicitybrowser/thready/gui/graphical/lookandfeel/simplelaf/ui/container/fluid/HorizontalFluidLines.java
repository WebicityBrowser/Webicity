package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.fluid;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundRenderedUnit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundRenderedUnitGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator.GenerationResult;

public class HorizontalFluidLines implements FluidLines {

	private final GlobalRenderContext renderContext;
	private final AbsoluteSize maxBounds;
	private final ContextSwitch[] switches;
	
	private List<ChildLayoutResult> rendered = new ArrayList<>();
	private float posX = 0;
	private float posY = 0;
	private float totalWidth = 0;
	private float curLineHeight = 0;

	public HorizontalFluidLines(GlobalRenderContext renderContext, AbsoluteSize maxBounds) {
		this.renderContext = renderContext;
		this.maxBounds = maxBounds;
		this.switches = new ContextSwitch[] {};
	}

	@Override
	public void addBox(BoundBox<?, ?> child) {
		BoundRenderedUnitGenerator<?> unitGenerator = child.render(renderContext, createLocalRenderContext(switches));
		boolean lineStart = true;
		while (!unitGenerator.getRaw().completed()) { // A fluid box has multiple units to add to the line
			AbsoluteSize preferredSize = calculatePreferredSize();
			boolean forceFit = lineStart;
			GenerationResult generationResult = unitGenerator.getRaw().generateNextUnit(preferredSize, forceFit);
			if (generationResult == GenerationResult.NO_FIT) {
				goToNextLine();
				lineStart = true;
			} else {
				addUnitToLine(unitGenerator.getLastGeneratedUnit());
				lineStart = false;
			}
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

	private void addUnitToLine(BoundRenderedUnit<?> unit) {
		AbsoluteSize size = unit.getRaw().preferredSize();
		curLineHeight = Math.max(size.height(), curLineHeight);
		
		AbsolutePosition startPosition = new AbsolutePosition(posX, posY);
		Rectangle relativeRect = new Rectangle(startPosition, size);
		
		rendered.add(new ChildLayoutResult(relativeRect, unit));
		
		posX += size.width();
	}
	
	private void goToNextLine() {
		posX = 0;
		posY += curLineHeight;
		curLineHeight = 0;
	}
	
	private AbsoluteSize calculatePreferredSize() {
		float remainingWidth = maxBounds.width() - posX;
		return new AbsoluteSize(remainingWidth, RelativeDimension.UNBOUNDED);
	}
	
}
