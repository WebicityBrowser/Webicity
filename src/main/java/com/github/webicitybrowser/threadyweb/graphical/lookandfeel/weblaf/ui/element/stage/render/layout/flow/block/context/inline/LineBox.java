package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.block.context.inline;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.util.AbsolutePositionMath;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.cursor.CursorTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.cursor.LineCursorTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.cursor.LineDimensionConverter;

public class LineBox {

	private final CursorTracker cursorTracker;
	
	private final List<LineBoxRenderResult> lineItems = new ArrayList<>();

	public LineBox(LineDimensionConverter dimensionConverter) {
		this.cursorTracker = new LineCursorTracker(dimensionConverter);
	}
	
	public void add(Unit unit) {
		AbsoluteSize size = unit.getMinimumSize();
		AbsolutePosition startPosition = cursorTracker.getNextPosition();
		lineItems.add(new LineBoxRenderResult(unit, startPosition));
		cursorTracker.add(size);
	}
	
	public boolean canFit(AbsoluteSize unitSize, AbsoluteSize maxLineSize) {
		return cursorTracker.addWillOverflowLine(unitSize, maxLineSize);
	}
	
	public AbsoluteSize getSize() {
		return cursorTracker.getSizeCovered();
	};
	
	private record LineBoxRenderResult(Unit unit, AbsolutePosition position) {}

	public List<FluidChildRenderResult> getRenderResults(AbsolutePosition linePosition) {
		List<FluidChildRenderResult> renderResults = new ArrayList<>();
		for (LineBoxRenderResult lineItem: lineItems) {
			renderResults.add(createFinalRenderResult(lineItem, linePosition));
		}
		
		return renderResults;
	}

	private FluidChildRenderResult createFinalRenderResult(LineBoxRenderResult lineItem, AbsolutePosition linePosition) {
		AbsolutePosition itemPosition = AbsolutePositionMath.sum(lineItem.position(), linePosition);
		return new FluidChildRenderResult(lineItem.unit(), itemPosition);
	}
	
}
