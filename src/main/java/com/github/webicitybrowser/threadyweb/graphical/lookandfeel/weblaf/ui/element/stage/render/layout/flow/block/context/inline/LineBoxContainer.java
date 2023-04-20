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
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.cursor.LineCursorTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.cursor.LineDimension;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.cursor.LineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.inline.FlowRecursiveContextSwitch;

public class LineBoxContainer {

	private final RenderContext renderContext;
	private final AbsoluteSize maxBounds;
	private final LineDimensionConverter dimensionConverter;
	private final ContextSwitch[] switches;
	
	private final List<LineBox> lineBoxes = new ArrayList<>();
	
	private LineBox currentLine;
	private UnitGenerator currentFlow;

	public LineBoxContainer(RenderContext renderContext, AbsoluteSize maxBounds, LineDimensionConverter dimensionConverter) {
		this.renderContext = renderContext;
		this.maxBounds = maxBounds;
		this.dimensionConverter = dimensionConverter;
		this.switches = createContextSwitches();
		goToNextLine();
	}

	public void addBox(Box child) {
		if (child instanceof FluidBox fluidBox) {
			addFluidBox(fluidBox);
		} else {
			addSolidBox(child);
		}
	}

	public LineBoxContainerResult collectRenderResults() {
		List<FluidChildRenderResult> renderedChildren = new ArrayList<>();
		CursorTracker cursorTracker = new LineCursorTracker(dimensionConverter);
		for (LineBox line: lineBoxes) {
			AbsolutePosition linePosition = cursorTracker.getNextPosition();
			renderedChildren.addAll(line.getRenderResults(linePosition));
			cursorTracker.add(line.getSize());
			cursorTracker.nextLine();
		}
		return new LineBoxContainerResult(
			cursorTracker.getSizeCovered(),
			renderedChildren.toArray(FluidChildRenderResult[]::new));
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
		LineDimension maxLineBounds = dimensionConverter.getLineDimension(maxBounds);
		LineDimension childLineBounds = new LineDimension(maxLineBounds.run(), -1);
		AbsoluteSize childBounds = dimensionConverter.getAbsoluteSize(childLineBounds);
		Unit unit = childRenderer.render(renderContext, childBounds);
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
		return currentLine.canFit(unitSize, maxBounds);
	}

	private void addMergedUnitToLine(Unit unit) {
		currentLine.add(unit);
	}
	
	private void goToNextLine() {
		currentLine = new LineBox(dimensionConverter);
		lineBoxes.add(currentLine);
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
