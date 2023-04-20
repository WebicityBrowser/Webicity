package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.block.context.inline;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.FluidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.FluidRenderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.Renderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.PartialUnitPreview;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.UnitGenerator;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.inline.FlowRecursiveContextSwitch;

public class HorizontalFluidLines implements FluidLines {

	private final RenderContext renderContext;
	private final AbsoluteSize maxBounds;
	private final ContextSwitch[] switches;
	
	private UnitGenerator currentFlow;
	private List<FluidChildrenResult> rendered = new ArrayList<>();
	private float posX = 0;
	private float posY = 0;
	private float totalWidth = 0;
	private float curLineHeight = 0;

	public HorizontalFluidLines(RenderContext renderContext, AbsoluteSize maxBounds) {
		this.renderContext = renderContext;
		this.maxBounds = maxBounds;
		this.switches = createContextSwitches();
	}

	@Override
	public void addBox(Box child) {
		if (child instanceof FluidBox fluidBox) {
			addFluidBox(fluidBox);
		} else {
			addSolidBox(child);
		}
	}

	@Override
	public AbsoluteSize computeTotalSize() {
		return new AbsoluteSize(totalWidth, posY + curLineHeight);
	}
	
	@Override
	public FluidChildrenResult[] getRenderResults() {
		return rendered.toArray(FluidChildrenResult[]::new);
	}
	
	private void addFluidBox(FluidBox child) {
		FluidRenderer childRenderer = child.createRenderer();
		currentFlow = childRenderer.renderFluid(renderContext, switches);
		while (!currentFlow.completed()) { // A fluid box has multiple units to add to the line
			PartialUnitPreview partialUnitPreview = currentFlow.previewNextUnit();
			startNewLineIfNeeded(currentFlow, partialUnitPreview);
			partialUnitPreview.append(); // Appends to the current merged unit
		}
		addMergedUnitToLine(currentFlow.getMergedUnits());
		currentFlow = null;
	}
	
	private void addSolidBox(Box child) {
		Renderer childRenderer = child.createRenderer();
		Unit unit = childRenderer.render(renderContext, new AbsoluteSize(maxBounds.width(), -1));
		goToNextLine();
		addMergedUnitToLine(unit);
		goToNextLine();
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
		totalWidth = Math.max(totalWidth, posX);
	}
	
	private void goToNextLine() {
		posX = 0;
		posY += curLineHeight;
		curLineHeight = 0;
	}
	
	private void commitCurrentFlow() {
		if (currentFlow != null) {
			addMergedUnitToLine(currentFlow.getMergedUnits());
		}
	}
	
	private ContextSwitch[] createContextSwitches() {
		return new ContextSwitch[] { new RecursiveContextSwitchHandler() };
	}
	
	public void handleOutOfFlowBox(Box box) {
		commitCurrentFlow();
		addSolidBox(box);
	}

	private class RecursiveContextSwitchHandler implements FlowRecursiveContextSwitch {

		@Override
		public void onBoxEnter(BoxEnterContext context) {
			if (!(context.getBox() instanceof FluidBox)) {
				context.skipBox();
				handleOutOfFlowBox(context.getBox());
			}
		}
		
	}
	
}
