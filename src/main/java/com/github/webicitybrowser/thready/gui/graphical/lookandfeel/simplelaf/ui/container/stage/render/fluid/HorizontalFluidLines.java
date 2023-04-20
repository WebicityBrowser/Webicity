package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.stage.render.fluid;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.FluidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.FluidRenderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.PartialUnitPreview;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.UnitGenerator;

public class HorizontalFluidLines implements FluidLines {

	private final RenderContext renderContext;
	private final AbsoluteSize maxBounds;
	private final ContextSwitch[] switches;
	
	private List<FluidChildrenResult> rendered = new ArrayList<>();
	private float posX = 0;
	private float posY = 0;
	private float totalWidth = 0;
	private float curLineHeight = 0;

	public HorizontalFluidLines(RenderContext renderContext, AbsoluteSize maxBounds) {
		this.renderContext = renderContext;
		this.maxBounds = maxBounds;
		this.switches = new ContextSwitch[] {};
	}

	@Override
	public void addBox(FluidBox child) {
		FluidRenderer childRenderer = child.createRenderer();
		UnitGenerator unitGenerator = childRenderer.renderFluid(renderContext, switches);
		while (!unitGenerator.completed()) { // A fluid box has multiple units to add to the line
			PartialUnitPreview partialUnitPreview = unitGenerator.previewNextUnit();
			startNewLineIfNeeded(unitGenerator, partialUnitPreview);
			partialUnitPreview.append(); // Appends to the current merged unit
		}
		addMergedUnitToLine(unitGenerator.getMergedUnits());
	}

	@Override
	public AbsoluteSize computeTotalSize() {
		return new AbsoluteSize(totalWidth, posY + curLineHeight);
	}
	
	@Override
	public FluidChildrenResult[] getRenderResults() {
		return rendered.toArray(FluidChildrenResult[]::new);
	}
	
	private void startNewLineIfNeeded(UnitGenerator unitGenerator, PartialUnitPreview partialUnitPreview) {
		if (lineCanNotFit(partialUnitPreview)) {
			addMergedUnitToLine(unitGenerator.getMergedUnits());
			goToNextLine();
		}
	}

	private boolean lineCanNotFit(PartialUnitPreview PartialUnitPreview) {
		if (maxBounds.width() == -1) {
			return false;
		}
		
		float unitEndX = PartialUnitPreview.sizeAfterAppend().width() + posX;
		return unitEndX > maxBounds.width();
	}

	private void addMergedUnitToLine(Unit unit) {
		AbsoluteSize size = unit.getMinimumSize();
		curLineHeight = Math.max(size.height(), curLineHeight);
		
		AbsolutePosition startPosition = new AbsolutePosition(posX, posY);
		rendered.add(new FluidChildrenResult(startPosition, unit));
		
		posX += unit.getMinimumSize().width();
	}
	
	private void goToNextLine() {
		posX = 0;
		posY += curLineHeight;
		curLineHeight = 0;
	}
	
}
