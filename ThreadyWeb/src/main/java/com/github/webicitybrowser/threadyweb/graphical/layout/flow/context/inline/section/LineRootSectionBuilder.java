package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.section;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.dimensions.util.AbsoluteDimensionsMath;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.section.LineSectionUtil.ChildEntry;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor.LineDimension;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor.LineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor.LineDimension.LineDirection;

public class LineRootSectionBuilder implements LineSectionBuilder {

	private final List<ChildLayoutResult> childLayoutResults = new ArrayList<>();
	private final List<ChildEntry> childEntries = new ArrayList<>();
	private final LineDimension offsetPosition;

	private float totalDepth = 0;

	public LineRootSectionBuilder(LineDimension offsetPosition) {
		this.offsetPosition = offsetPosition;
	}

	@Override
	public void addUnit(RenderedUnit unit, LineDimension adjustedSize, LineDimension itemPosition) {
		totalDepth = Math.max(totalDepth, adjustedSize.depth());
		LineDimension adjustedPosition = AbsoluteDimensionsMath.sum(
			itemPosition, offsetPosition,
			(run, depth) -> new LineDimension(run, depth, offsetPosition.direction()));
		childEntries.add(new ChildEntry(unit, adjustedSize, adjustedPosition));
	}

	@Override
	public void finalize(AbsoluteSize sectionSize) {
		LineDirection lineDirection = offsetPosition.direction();
		LineDimension totalLineSize = LineSectionUtil.determineTotalLineSize(childEntries, lineDirection);
		AbsoluteSize absoluteTotalLineSize = LineDimensionConverter.convertToAbsoluteSize(totalLineSize);
		AbsoluteSize usedLineSize = new AbsoluteSize(
			sectionSize.width() != RelativeDimension.UNBOUNDED ? sectionSize.width() : absoluteTotalLineSize.width(),
			sectionSize.height() != RelativeDimension.UNBOUNDED ? sectionSize.height() : absoluteTotalLineSize.height()
		);
		childLayoutResults.clear();

		for (ChildEntry childEntry: childEntries) {
			AbsoluteSize itemSize = LineDimensionConverter.convertToAbsoluteSize(childEntry.adjustedSize());
			AbsolutePosition itemPosition = LineDimensionConverter.convertToAbsolutePosition(childEntry.position(), usedLineSize, itemSize);
			Rectangle itemBounds = new Rectangle(itemPosition, itemSize);
			childLayoutResults.add(new ChildLayoutResult(childEntry.unit(), itemBounds));
		}
	}

	public List<ChildLayoutResult> getChildLayoutResults() {
		return childLayoutResults;
	}

}
