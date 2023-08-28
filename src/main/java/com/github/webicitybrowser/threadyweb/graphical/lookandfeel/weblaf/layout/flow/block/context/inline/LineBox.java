package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Function;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.marker.LineMarker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.marker.UnitEnterMarker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.marker.UnitExitMarker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.CursorTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineCursorTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.BuildableRenderedUnit;

public class LineBox {

	private final CursorTracker cursorTracker;
	private final LineDimensionConverter dimensionConverter;
	private final Function<DirectivePool, BuildableRenderedUnit> innerUnitGenerator;
	
	private final List<LineEntry> lineItems = new ArrayList<>();
	private final List<LineMarker> activeMarkers = new ArrayList<>();

	public LineBox(LineDimensionConverter dimensionConverter, Function<DirectivePool, BuildableRenderedUnit> innerUnitGenerator) {
		this.cursorTracker = new LineCursorTracker(dimensionConverter);
		this.dimensionConverter = dimensionConverter;
		this.innerUnitGenerator = innerUnitGenerator;
	}
	
	public boolean isEmpty() {
		return cursorTracker.getSizeCovered().width() == 0;
	}

	public void add(RenderedUnit unit) {
		AbsoluteSize size = unit.preferredSize();
		AbsolutePosition startPosition = cursorTracker.getNextPosition();
		lineItems.add(new LineBoxRenderResultEntry(unit, startPosition));
		cursorTracker.add(size);
	}

	public void addMarker(LineMarker marker) {
		lineItems.add(new LineMarkerEntry(marker, cursorTracker.getNextPosition()));
		if (marker instanceof UnitEnterMarker unitEnterMarker) {
			activeMarkers.add(unitEnterMarker);
			cursorTracker.add(new AbsoluteSize(unitEnterMarker.leftEdgeSize(), unitEnterMarker.topEdgeSize()));
		} else if (marker instanceof UnitExitMarker unitExitMarker) {
			activeMarkers.remove(activeMarkers.size() - 1);
			cursorTracker.add(new AbsoluteSize(unitExitMarker.rightEdgeSize(), unitExitMarker.bottomEdgeSize()));
		}
    }
	
	public boolean canFit(AbsoluteSize unitSize, AbsoluteSize maxLineSize) {
		return
			(unitSize.width() == 0 && unitSize.height() == 0) ||
			!cursorTracker.addWillOverflowLine(unitSize, maxLineSize);
	}
	
	public AbsoluteSize getSize() {
		return cursorTracker.getSizeCovered();
	};

	public List<LineMarker> getActiveMarkers() {
		return activeMarkers;
	}
	
	public List<ChildLayoutResult> layoutAtPos(AbsolutePosition linePosition) {
		CursorTracker cursorTracker = new LineCursorTracker(dimensionConverter);
		Stack<LineSectionBuilder> sectionBuilderStack = new Stack<>();
		LineRootSectionBuilder rootSectionBuilder = new LineRootSectionBuilder(linePosition);
		sectionBuilderStack.push(rootSectionBuilder);
		addLineItemsToSectionBuilders(sectionBuilderStack, cursorTracker);

		closeAllSubsections(sectionBuilderStack, cursorTracker);
		
		return rootSectionBuilder.getChildLayoutResults();
	}

	//

	private void addLineItemsToSectionBuilders(Stack<LineSectionBuilder> sectionBuilderStack, CursorTracker cursorTracker) {
		for (LineEntry lineEntry: lineItems) {
			if (lineEntry instanceof LineMarkerEntry lineMarkerEntry) {
				handleMarkerLayout(sectionBuilderStack, lineMarkerEntry.marker(), cursorTracker);
			} else if (lineEntry instanceof LineBoxRenderResultEntry lineItem) {
				handleBoxLayout(sectionBuilderStack, lineItem, cursorTracker);
			}
		}
	}

	private void handleMarkerLayout(Stack<LineSectionBuilder> sectionStack, LineMarker marker, CursorTracker cursorTracker) {
		if (marker instanceof UnitEnterMarker unitEnterMarker) {
			BuildableRenderedUnit innerUnit = innerUnitGenerator.apply(unitEnterMarker.styleDirectives());
			sectionStack.push(new LineSubsectionBuilder(cursorTracker.getNextPosition(), innerUnit));
		} else if (marker instanceof UnitExitMarker unitExitMarker) {
			cursorTracker.add(new AbsoluteSize(unitExitMarker.rightEdgeSize(), unitExitMarker.bottomEdgeSize()));
			closeSubsection(sectionStack, cursorTracker, true);
		}
	}

	private void handleBoxLayout(Stack<LineSectionBuilder> sectionBuilderStack, LineBoxRenderResultEntry lineItem, CursorTracker cursorTracker) {
		LineSectionBuilder sectionBuilder = sectionBuilderStack.peek();
		Rectangle lineItemBounds = new Rectangle(
			cursorTracker.getNextPosition(),
			lineItem.unit().preferredSize());
		sectionBuilder.addUnit(lineItemBounds, lineItem.unit());
		cursorTracker.add(lineItemBounds.size());
	}

	private void closeAllSubsections(Stack<LineSectionBuilder> sectionBuilderStack, CursorTracker cursorTracker) {
		while (sectionBuilderStack.size() > 1) {
			closeSubsection(sectionBuilderStack, cursorTracker, false);
		}
	}
	
	private void closeSubsection(Stack<LineSectionBuilder> sectionStack, CursorTracker cursorTracker, boolean markedFinished) {
		LineSubsectionBuilder subsectionBuilder = (LineSubsectionBuilder) sectionStack.pop();
		subsectionBuilder.finalize(cursorTracker.getNextPosition());
		if (markedFinished) {
			subsectionBuilder.getUnit().markFinished();
		}
		Rectangle subsectionBounds = new Rectangle(
			subsectionBuilder.getStartPosition(),
			subsectionBuilder.getUnit().preferredSize());
		LineSectionBuilder parentSectionBuilder = sectionStack.peek();
		parentSectionBuilder.addUnit(subsectionBounds, subsectionBuilder.getUnit());
	}

	private interface LineEntry {}
	private record LineMarkerEntry(LineMarker marker, AbsolutePosition position) implements LineEntry {}
	private record LineBoxRenderResultEntry(RenderedUnit unit, AbsolutePosition position) implements LineEntry {}
	
}
