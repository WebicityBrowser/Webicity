package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.util.AbsolutePositionMath;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.CursorTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineCursorTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimensionConverter;

public class LineBox {

	private final CursorTracker cursorTracker;
	
	private final List<LineBoxRenderResult> lineItems = new ArrayList<>();

	public LineBox(LineDimensionConverter dimensionConverter) {
		this.cursorTracker = new LineCursorTracker(dimensionConverter);
	}
	
	public void add(RenderedUnit unit) {
		AbsoluteSize size = unit.preferredSize();
		AbsolutePosition startPosition = cursorTracker.getNextPosition();
		lineItems.add(new LineBoxRenderResult(unit, startPosition));
		cursorTracker.add(size);
	}
	
	public boolean canFit(AbsoluteSize unitSize, AbsoluteSize maxLineSize) {
		return !cursorTracker.addWillOverflowLine(unitSize, maxLineSize);
	}
	
	public AbsoluteSize getSize() {
		return cursorTracker.getSizeCovered();
	};

	public List<ChildLayoutResult> layoutAtPos(AbsolutePosition linePosition) {
		List<ChildLayoutResult> renderResults = new ArrayList<>();
		for (LineBoxRenderResult lineItem: lineItems) {
			renderResults.add(createFinalRenderResult(lineItem, linePosition));
		}
		
		return renderResults;
	}

	private ChildLayoutResult createFinalRenderResult(LineBoxRenderResult lineItem, AbsolutePosition linePosition) {
		AbsolutePosition itemPosition = AbsolutePositionMath.sum(lineItem.position(), linePosition);
		AbsoluteSize itemSize = lineItem.unit().preferredSize();
		Rectangle childRect = new Rectangle(itemPosition, itemSize);
		return new ChildLayoutResult(childRect, lineItem.unit());
	}
	
	private record LineBoxRenderResult(RenderedUnit unit, AbsolutePosition position) {}
	
}
