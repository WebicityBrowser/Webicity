package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Function;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.marker.LineMarker;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.marker.UnitEnterMarker;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.marker.UnitExitMarker;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.section.LineRootSectionBuilder;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.section.LineSectionBuilder;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.section.LineSubsectionBuilder;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor.CursorTracker;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor.LineCursorTracker;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor.LineDimension;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor.LineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor.LineDimension.LineDirection;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.BuildableRenderedUnit;

// TODO: This class is hefty, needs to be broken down
public class LineBox {

	private final CursorTracker cursorTracker;
	private final DirectivePool lineStyles;
	private final LineDirection lineDirection;
	private final LineDimension maxLineSize;
	private final Function<DirectivePool, BuildableRenderedUnit> innerUnitGenerator;
	
	private final List<LineEntry> lineItems = new ArrayList<>();
	private final List<LineMarker> activeMarkers = new ArrayList<>();

	private AbsolutePosition estimatedPosition;
	private float minLineSize = 0;

	public LineBox(LineDimension maxLineSize, DirectivePool lineStyles, Function<DirectivePool, BuildableRenderedUnit> innerUnitGenerator) {
		this.lineDirection = maxLineSize.direction();
		this.lineStyles = lineStyles;
		this.cursorTracker = new LineCursorTracker(lineDirection);
		this.maxLineSize = maxLineSize;
		this.innerUnitGenerator = innerUnitGenerator;
	}
	
	public boolean isEmpty() {
		return cursorTracker.getSizeCovered().width() == 0;
	}

	public void add(RenderedUnit unit, AbsoluteSize adjustedSize) {
		LineDimension startPosition = cursorTracker.getNextPosition();
		lineItems.add(new LineBoxRenderResultEntry(unit, startPosition, adjustedSize));
		cursorTracker.add(adjustedSize);
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
	
	public boolean canFit(AbsoluteSize unitSize) {
		return
			(unitSize.width() == 0 && unitSize.height() == 0) ||
			!cursorTracker.addWillOverflowLine(unitSize, maxLineSize);
	}
	
	public AbsoluteSize getSize() {
		AbsoluteSize sizeCovered = cursorTracker.getSizeCovered();
		return new AbsoluteSize(
			sizeCovered.width(),
			Math.max(sizeCovered.height(), minLineSize));
	};

	public LineDimension getMaxLineSize() {
		return maxLineSize;
	}

	public List<LineMarker> getActiveMarkers() {
		return activeMarkers;
	}
	
	public List<ChildLayoutResult> layoutAtPos(LineDimension linePosition) {
		CursorTracker cursorTracker = new LineCursorTracker(lineDirection);
		Stack<LineSectionBuilder> sectionBuilderStack = new Stack<>();
		LineRootSectionBuilder rootSectionBuilder = new LineRootSectionBuilder(linePosition);
		sectionBuilderStack.push(rootSectionBuilder);
		addLineItemsToSectionBuilders(sectionBuilderStack, cursorTracker);

		closeAllSubsections(sectionBuilderStack, cursorTracker);
		
		return rootSectionBuilder.getChildLayoutResults();
	}

	public List<LineEntry> getLineItems() {
		return lineItems;
	}

	public AbsolutePosition getEstimatedPosition() {
		return estimatedPosition;
	}

	public void setEstimatedPosition(AbsolutePosition estimatedPosition) {
		this.estimatedPosition = estimatedPosition;
	}

	public float getEstimatedBlockSize() {
		float calculatedSize = LineDimensionConverter
			.convertToLineDimension(cursorTracker.getSizeCovered(), lineDirection)
			.depth();
		return Math.max(calculatedSize, minLineSize);
	}

	public LineDirection getLineDirection() {
		return lineDirection;
	}

	public DirectivePool getLineStyles() {
		return lineStyles;
	}

	public void ensureMinHeight(float newMinSize) {
		minLineSize = Math.max(minLineSize, newMinSize);
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
		LineDimension lineItemPosition = cursorTracker.getNextPosition();
		LineDimension lineItemSize = LineDimensionConverter.convertToLineDimension(lineItem.size(), lineDirection);
		sectionBuilder.addUnit(lineItem.unit(), lineItemSize, lineItemPosition);
		cursorTracker.add(lineItem.size());
	}

	private void closeAllSubsections(Stack<LineSectionBuilder> sectionBuilderStack, CursorTracker cursorTracker) {
		while (sectionBuilderStack.size() > 1) {
			closeSubsection(sectionBuilderStack, cursorTracker, false);
		}

		AbsoluteSize parentSize = LineDimensionConverter.convertToAbsoluteSize(maxLineSize);
		sectionBuilderStack.peek().finalize(parentSize);
	}
	
	private void closeSubsection(Stack<LineSectionBuilder> sectionStack, CursorTracker cursorTracker, boolean markedFinished) {
		LineSubsectionBuilder subsectionBuilder = (LineSubsectionBuilder) sectionStack.pop();
		subsectionBuilder.finalize(new AbsoluteSize(-1, -1));
		if (markedFinished) {
			subsectionBuilder.getUnit().markFinished();
		}

		LineDimension subsectionSize = LineDimensionConverter.convertToLineDimension(subsectionBuilder.getUnit().fitSize(), lineDirection);
		LineDimension subsectionPosition = subsectionBuilder.getStartPosition();

		LineSectionBuilder parentSectionBuilder = sectionStack.peek();
		parentSectionBuilder.addUnit(subsectionBuilder.getUnit(), subsectionSize, subsectionPosition);
	}

	public interface LineEntry {}
	public record LineMarkerEntry(LineMarker marker, LineDimension startPosition) implements LineEntry {}
	public record LineBoxRenderResultEntry(RenderedUnit unit, LineDimension startPosition, AbsoluteSize size) implements LineEntry {}
	
}
