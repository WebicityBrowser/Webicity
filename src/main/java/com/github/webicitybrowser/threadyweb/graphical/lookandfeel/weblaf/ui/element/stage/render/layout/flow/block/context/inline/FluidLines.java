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
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.cursor.CursorTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.inline.FlowRecursiveContextSwitch;

public class FluidLines {

	private final RenderContext renderContext;
	private final AbsoluteSize maxBounds;
	private final CursorTracker cursorTracker;
	private final ContextSwitch[] switches;
	
	private UnitGenerator currentFlow;
	private List<FluidChildrenResult> rendered = new ArrayList<>();

	public FluidLines(RenderContext renderContext, AbsoluteSize maxBounds, CursorTracker cursorTracker) {
		this.renderContext = renderContext;
		this.maxBounds = maxBounds;
		this.cursorTracker = cursorTracker;
		this.switches = createContextSwitches();
	}

	public void addBox(Box child) {
		if (child instanceof FluidBox fluidBox) {
			addFluidBox(fluidBox);
		} else {
			addSolidBox(child);
		}
	}

	public AbsoluteSize computeTotalSize() {
		return cursorTracker.getSizeCovered();
	}
	
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
		goToNextLine();
		Renderer childRenderer = child.createRenderer();
		AbsoluteSize lineSize = cursorTracker.getFullLineSize(maxBounds);
		Unit unit = childRenderer.render(renderContext, lineSize);
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
		AbsoluteSize unitSize = PartialUnitPreview.sizeAfterAppend();
		return cursorTracker.addWillOverflowLine(unitSize, maxBounds);
	}

	private void addMergedUnitToLine(Unit unit) {
		AbsoluteSize size = unit.getMinimumSize();
		AbsolutePosition startPosition = cursorTracker.getNextPosition();
		rendered.add(new FluidChildrenResult(startPosition, unit));
		cursorTracker.add(size);
	}
	
	private void goToNextLine() {
		cursorTracker.nextLine();
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
