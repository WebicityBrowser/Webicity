package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
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
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.CursorTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineCursorTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimension;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.inline.FlowRecursiveContextSwitch;

public class LineBoxContainer {

	private final GlobalRenderContext globalRenderContext;
	private final ContextSwitch[] contextSwitches;
	private final AbsoluteSize maxBounds;
	private final LineDimensionConverter dimensionConverter;
	
	private final List<LineBox> lineBoxes = new ArrayList<>();
	
	private LineBox currentLine;
	private BoundRenderedUnitGenerator<?> currentFlow;

	public LineBoxContainer(
		GlobalRenderContext renderContext, AbsoluteSize maxBounds, LineDimensionConverter dimensionConverter, ContextSwitch[] contextSwitches
	) {
		this.globalRenderContext = renderContext;
		this.maxBounds = maxBounds;
		this.dimensionConverter = dimensionConverter;
		this.contextSwitches = mergeDefaultContextSwitches(contextSwitches);
		goToNextLine();
	}

	public void addBox(BoundBox<?, ?> child) {
		if (child.getRaw().isFluid()) {
			addFluidBox(child);
		} else {
			addSolidBox(child);
		}
	}

	public LayoutResult layout() {
		List<ChildLayoutResult> renderedChildren = new ArrayList<>();
		CursorTracker cursorTracker = new LineCursorTracker(dimensionConverter);
		for (LineBox line: lineBoxes) {
			AbsolutePosition linePosition = cursorTracker.getNextPosition();
			renderedChildren.addAll(line.layoutAtPos(linePosition));
			cursorTracker.add(line.getSize());
			cursorTracker.nextLine();
		}
		return LayoutResult.create(
			renderedChildren.toArray(ChildLayoutResult[]::new),
			cursorTracker.getSizeCovered());
	}
	
	private void addFluidBox(BoundBox<?, ?> child) {
		LocalRenderContext childRenderContext = LocalRenderContext.create(
			new AbsoluteSize(RelativeDimension.UNBOUNDED, RelativeDimension.UNBOUNDED),
			contextSwitches);
		currentFlow = child.render(globalRenderContext, childRenderContext);
		while (!currentFlow.getRaw().completed()) {
			BoundRenderedUnit<?> unit = appendNextUnit(false);
			if (unit == null) {
				goToNextLine();
				unit = appendNextUnit(true);
			}

			currentLine.add(unit);
		}
		currentFlow = null;
	}

	private BoundRenderedUnit<?> appendNextUnit(boolean forceFit) {
		// TODO: Obtain remaining bounds properly
		AbsoluteSize remainingLineSize = getMaxChildBounds();
		currentFlow.getRaw().generateNextUnit(remainingLineSize, forceFit);
		BoundRenderedUnit<?> unit = currentFlow.getLastGeneratedUnit();

		return unit;
	}
	
	private void addSolidBox(BoundBox<?, ?> child) {
		goToNextLine();
		AbsoluteSize maxChildBounds = getMaxChildBounds();
		LocalRenderContext childRenderContext = LocalRenderContext.create(maxChildBounds, contextSwitches);
		BoundRenderedUnitGenerator<?> unitGenerator = child.render(globalRenderContext, childRenderContext);
		GenerationResult generationResult = unitGenerator.getRaw().generateNextUnit(maxChildBounds, true);
		assert generationResult == GenerationResult.NORMAL && unitGenerator.getRaw().completed();
		currentLine.add(unitGenerator.getLastGeneratedUnit());
		goToNextLine();
	}

	private AbsoluteSize getMaxChildBounds() {
		LineDimension maxLineBounds = dimensionConverter.getLineDimension(maxBounds);
		LineDimension childLineBounds = new LineDimension(maxLineBounds.run(), -1);
		return dimensionConverter.getAbsoluteSize(childLineBounds);
	}
	
	private void goToNextLine() {
		currentLine = new LineBox(dimensionConverter);
		lineBoxes.add(currentLine);
	}
	
	private ContextSwitch[] mergeDefaultContextSwitches(ContextSwitch[] extraSwitches) {
		ContextSwitch[] allSwitches = new ContextSwitch[extraSwitches.length + 1];
		allSwitches[0] = new RecursiveContextSwitchHandler();
		System.arraycopy(extraSwitches, 0, allSwitches, 1, extraSwitches.length);
		return allSwitches;
	}
	
	public void handleOutOfFlowBox(BoundBox<?, ?> box) {
		// TODO: Make sure block boxes cause proper wrap again
		// commitCurrentFlow();
		addSolidBox(box);
	}

	private class RecursiveContextSwitchHandler implements FlowRecursiveContextSwitch {

		@Override
		public void onBoxEnter(BoxEnterContext context) {
			if (!(context.getBox().getRaw().isFluid())) {
				context.skipBox();
				handleOutOfFlowBox(context.getBox());
			}
		}
		
	}
	
}
